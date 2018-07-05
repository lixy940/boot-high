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

            //md5处理输入的密码
            String passwordParamMd5 = DigestUtils.md5DigestAsHex(loginUserVO.getPassword().getBytes());
            //todo 根据用户名获取经过md5处理后存储到db中的密码
            String dbPassword = "";
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
}
