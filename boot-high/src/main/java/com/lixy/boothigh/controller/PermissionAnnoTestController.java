package com.lixy.boothigh.controller;

import com.lixy.boothigh.anno.PermessionLimit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 17:54 2018/7/4
 * @Modified By:
 */
@RequestMapping("/permission")
@Controller
public class PermissionAnnoTestController {

    @PermessionLimit(superAdminUser=false,adminuser = true)
    @ResponseBody
    @GetMapping("/upDb")
    public String upDb(HttpServletRequest request) {
        /**
         * superAdminUser=false,adminuser = true，表示PermissionInterceptor要验证管理员权限，不严证超级管理员
         */
        return "login";
    }
}
