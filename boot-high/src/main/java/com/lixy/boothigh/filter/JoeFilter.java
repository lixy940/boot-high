/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:JoeFilter.java  
 * Package Name:com.lixy.filter
 * Author   Joe
 * Date:2017年11月6日下午6:01:33
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**  
 * ClassName:JoeFilter 
 * 自定义  Filter
 * 		@Order注解表示执行过滤顺序，值越小，越先执行
 * Date:     2017年11月6日 下午6:01:33
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Order(1)
@WebFilter(filterName = "joeFilter", urlPatterns = "/*")
public class JoeFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(JoeFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("--joe--初始化JoeFilter!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		logger.info(req.getRequestURL() + "---joe---> doFilter");
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("--joe--销毁JoeFilter!");
	}

}
  
