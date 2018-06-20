/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:SpringInterceptorRegister.java  
 * Package Name:com.lixy.config
 * Author   Joe
 * Date:2017年11月6日下午4:58:32
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.config;


import com.lixy.boothigh.interceptor.JoeInterceptor;
import com.lixy.boothigh.interceptor.LoginInterceptor;
import com.lixy.boothigh.interceptor.XbqInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**  
 * ClassName:SpringInterceptorRegister 
 * Spring 拦截器 注册器
 * Date:     2017年11月6日 下午4:58:32
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurerAdapter{

	/**
	 * TODO 添加spring中的拦截器.  
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有路径
		registry.addInterceptor(new XbqInterceptor()).addPathPatterns("/**");
		// 拦截/freemarker后路径
		registry.addInterceptor(new JoeInterceptor()).addPathPatterns("/freemarker/**");

		//注意对登录拦截的设置，不然无法访问
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/login/loginRegister", "/import/*", "/aop/*","/test/*","/list","/socktest/*")
				//后面是swagger拦截排除
				.excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/webjars/springfox-swagger-ui/**");
		super.addInterceptors(registry);
	}

	/**
	 * TODO  注册静态文件的自定义映射路径  
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/picture/");
        super.addResourceHandlers(registry);
    }


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/websocketBroadcast");
	}

}