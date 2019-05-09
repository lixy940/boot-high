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


import com.lixy.boothigh.constants.BConstant;
import com.lixy.boothigh.interceptor.JoeInterceptor;
import com.lixy.boothigh.interceptor.LoginInterceptor;
import com.lixy.boothigh.interceptor.PermissionInterceptor;
import com.lixy.boothigh.interceptor.XbqInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

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
				.excludePathPatterns("/login/loginRegister", "/import/*", "/aop/*","/test/*","/list","/permission/*",
						"/quartz/*","/excep/*")
				//后面是swagger拦截排除
				.excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs")
                .excludePathPatterns("/webjars/springfox-swagger-ui/**");
		//注册权限拦截器
		registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**");

		super.addInterceptors(registry);
	}

	/**
	 * TODO  注册静态文件的自定义映射路径  
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        super.addResourceHandlers(registry);

		//和页面有关的静态目录都放在项目的static目录下,例如访问http://localhost:8787/static/football.jpg,访问的是static目录下football.jpg
		//通过classpath指定resources的相对路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		//上传的图片在D盘下的photo_header目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
		//其中OTA表示访问的前缀。"file:D:/photo_header"是文件真实的存储路径，file:表示用的磁盘实际路径

		if (System.getProperty(BConstant.SYS_PROPERTY_OS_NAME).toLowerCase().contains(BConstant.SYS_OS_NAME_WINDOW)) {
			registry.addResourceHandler("/OTA/**").addResourceLocations("file:"+ BConstant.UPLOAD_PIC_WINDOWS);
			File file = new File(BConstant.UPLOAD_PIC_WINDOWS);
			if (!file.exists()) {
				file.mkdirs();
			}
		}else{
			registry.addResourceHandler("/OTA/**").addResourceLocations("file:"+BConstant.UPLOAD_PIC_LINUX);
			File file = new File(BConstant.UPLOAD_PIC_LINUX);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
    }

}