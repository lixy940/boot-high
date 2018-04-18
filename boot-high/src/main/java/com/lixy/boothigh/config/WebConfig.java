/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:WebConfig.java  
 * Package Name:com.lixy.config
 * Author   Joe
 * Date:2017年11月6日下午6:10:30
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.config;


import com.lixy.boothigh.filter.XbqFilter;
import com.lixy.boothigh.listener.XbqListener;
import com.lixy.boothigh.servlet.XbqServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * @Author: MR LIS
 * @Description: 通过 @bean 注入 servlet、filter、listener
 * @Date: 17:05 2018/4/18
 * @return
 */
@Configuration
public class WebConfig {

	/**
	 * @Author: MR LIS
	 * @Description: servletRegistrationBean:(使用代码注册Servlet（不需要@ServletComponentScan注解）).
	 * @Date: 17:06 2018/4/18
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(new XbqServlet());
		List<String> urlMappings = new ArrayList<String>();
		// 访问，可以添加多个
		urlMappings.add("/xbq/servlet");
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
	

	/**
	 * @Author: MR LIS
	 * @Description: getDemoFilter:(使用代码注册拦截器)
	 * @Date: 17:06 2018/4/18
	 * @return
	 */
	@Bean
	public FilterRegistrationBean getDemoFilter(){
		XbqFilter demoFilter = new XbqFilter();
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(demoFilter);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");							//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(1);
		return registrationBean;
	}

	/**
	 * @Author: MR LIS
	 * @Description: getDemoListener:(使用代码 引用 监听器)
	 * @Date: 17:06 2018/4/18
	 * @return
	 */
	@Bean
	public ServletListenerRegistrationBean<EventListener> getDemoListener(){
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new XbqListener());
		registrationBean.setOrder(1);
		return registrationBean;
	}
}