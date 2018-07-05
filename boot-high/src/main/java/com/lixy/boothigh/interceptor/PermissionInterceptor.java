package com.lixy.boothigh.interceptor;

import com.lixy.boothigh.anno.PermessionLimit;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截，和LoginInterceptor的登录拦截器有点重叠，可以将登录拦截去掉，通过注解方式去控制登录和管理员权限
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

//	@Resource
//	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		// if need admin
		boolean needAdminuser = false;
		boolean superAdminuser = false;
		HandlerMethod method = (HandlerMethod)handler;
		PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
		if (permission!=null) {
			needAdminuser = permission.adminuser();
			superAdminuser = permission.superAdminUser();
		}
		/**
		 * 判断管理员权限及超级管理员权限，如果为true,需要验证用户的权限是否符合要求
		 */
		if (needAdminuser) {
			//todo  获取当前用户并判断是否为管理员，如果不是管理员，return false
			boolean flag = false/*判断用户是否为管理员逻辑，此处暂时写flag为true*/;
			if(!flag) {
				response.setHeader("Content-Type", "application/json;charset=utf-8");
				response.getWriter().write("权限不够，需要管理员权限");
				return false;
			}
		}

		if (superAdminuser) {
			//todo  获取当前用户并判断是否为超级管理员，否则，return false
		}
		return super.preHandle(request, response, handler);
	}

}
