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

		// if need login
		boolean needLogin = true;
		boolean needAdminuser = false;
		HandlerMethod method = (HandlerMethod)handler;
		PermessionLimit permission = method.getMethodAnnotation(PermessionLimit.class);
		if (permission!=null) {
			needLogin = permission.limit();
			needAdminuser = permission.adminuser();
		}
		/**
		 * 判断是否需要登录或管理员权限
		 * 并验证是否登录或者是管理员，否则跳转到登录uri
		 */
		if (needLogin) {
			/*XxlConfUser loginUser = loginService.ifLogin(request);
			if (loginUser == null) {
				response.sendRedirect(request.getContextPath() + "/toLogin");
				//request.getRequestDispatcher("/toLogin").forward(request, response);
				return false;
			}
			if (needAdminuser && loginUser.getPermission()!=1) {
				throw new RuntimeException("权限拦截");
			}
			request.setAttribute(LoginService.LOGIN_IDENTITY, loginUser);*/
		}

		return super.preHandle(request, response, handler);
	}

}
