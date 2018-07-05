package com.lixy.boothigh.controller;


import com.alibaba.fastjson.JSONObject;
import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.utils.CookieUtil;
import com.lixy.boothigh.vo.LoginUserVO;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * @Author: MR LIS
 * @Description:登录登出，通过loginInterceptor进行拦截，此处的session放入的是redis
 * @Date: Create in 16:34 2018/4/18
 * @Modified By:
 */
@Api(tags = {"登录服务接口"})
@RequestMapping("/login")
@RestController
public class LoginController {

    /***
     * 用户登录
     *
     * @param loginUserVO
     * @return
     */
    @SystemControllerLog(methodDesc = "loginRegisterControllerAop")
    @ApiOperation(value = "用户登录", notes = "用户注册或登录", consumes = "application/json", response = JsonResult.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(HttpServletRequest request, HttpServletResponse response,HttpSession session, LoginUserVO loginUserVO) {
        JsonResult jsonResult = new JsonResult();
        try {

            //判断是否在cookie存在，通过cookie中的登录对象和数据库进行比较，如果返回不为空，表示登录状态未失效
            if (ifLogin(request) != null) {
                return jsonResult;
            }

            //md5处理输入的密码
            String passwordParamMd5 = DigestUtils.md5DigestAsHex(loginUserVO.getPassword().getBytes());
            //todo 根据用户名获取经过md5处理后存储到db中的密码
            String dbPassword = "db.password";//根据用户名获取经过md5处理后存储到db中的密码
            if (!dbPassword.equals(passwordParamMd5)) {
                jsonResult.setState(-1);
                jsonResult.setMessage("账号或密码错误");
            }
            //设置到session
            session.setAttribute(BConstant.CURRENT_USER_KEY, loginUserVO);

            //生成token,根据需要生成唯一的token
            String loginToken = makeToken(loginUserVO);
            // set cookie
            CookieUtil.set(response, BConstant.COOKIE_USER_KEY, loginToken, true);

        }  catch (Exception e) {

        }


        return new JsonResult();
    }


    /***
     * 用户退出
     *
     * @return
     */
    @SystemControllerLog(methodDesc = "logoutControllerAop")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response,HttpSession session) {

        try {
            // 移除用户登录登录信息和sessionid
            session.removeAttribute(BConstant.CURRENT_USER_KEY);
            //移除cookie
            CookieUtil.remove(request, response, BConstant.COOKIE_USER_KEY);
        }catch (Exception e) {

        }
        return new JsonResult();

    }


    private String makeToken(LoginUserVO loginUserVO){
        String tokenJson = JSONObject.toJSONString(loginUserVO);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private LoginUserVO parseToken(String tokenHex){
        LoginUserVO loginUserVO = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            loginUserVO = JSONObject.parseObject(tokenJson, LoginUserVO.class);
        }
        return loginUserVO;
    }
    /**
     * logout
     *
     * @param request
     * @return
     */
    public LoginUserVO ifLogin(HttpServletRequest request){
        String cookieToken = CookieUtil.getValue(request, BConstant.COOKIE_USER_KEY);
        if (cookieToken != null) {
            LoginUserVO cookieUser = parseToken(cookieToken);
            if (cookieUser != null) {
                //根据用户名查询用户信息
                LoginUserVO dbUser = null/*userDao.selectUserByName(cookieUser.getUsername())*/;
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return cookieUser;
                    }
                }
            }
        }
        return null;
    }
}
