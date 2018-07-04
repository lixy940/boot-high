package com.lixy.boothigh.controller;

import com.lixy.boothigh.anno.PermessionLimit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 17:54 2018/7/4
 * @Modified By:
 */
@RequestMapping("/import")
@Controller
public class PermissionAnnoTestController {

    @RequestMapping("/toLogin")
    @PermessionLimit(limit=false)
    public String toLogin(Model model, HttpServletRequest request) {
        /**
         * 进入该方法需要通过PermissionInterceptor，因为当前跳转到登录页面，如果不需要登录limit=false
         * 如果需要管理员身份，配置adminuser=true
         */
        return "login";
    }
}
