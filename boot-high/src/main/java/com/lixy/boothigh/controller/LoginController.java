package com.lixy.boothigh.controller;


import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.vo.LoginUserVO;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * 用户注册或登录
     *
     * @param loginUserVO
     * @return
     */
    @SystemControllerLog(methodDesc = "loginRegisterControllerAop")
    @ApiOperation(value = "用户注册或登录", notes = "用户注册或登录", consumes = "application/json", response = JsonResult.class)
    @RequestMapping(value = "/loginRegister", method = RequestMethod.POST)
    public JsonResult loginOrRegister(HttpServletRequest request, HttpSession session, LoginUserVO loginUserVO) {
        try {

            //todo 验证登录信息
            //设置到session
            session.setAttribute(BConstant.CURRENT_USER_KEY, loginUserVO);


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
    public JsonResult logout(HttpSession session) {

        try {
            // 移除用户登录登录信息和sessionid
            session.removeAttribute(BConstant.CURRENT_USER_KEY);



        }catch (Exception e) {

        }
        return new JsonResult();

    }
}
