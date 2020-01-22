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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**  
 * ClassName:JoeFilter 
 * 自定义 Serlvlet 的过滤器 
 * Date:     2017年11月6日 下午6:01:33
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class XbqFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(XbqFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("--xbq--初始化JoeFilter!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
//		logger.info(req.getRequestURL() + "---xbq---> doFilter ");
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("--xbq--销毁JoeFilter!");
	}

}
  
