package com.lixy.boothigh;

import com.lixy.boothigh.constants.BConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@ServletComponentScan
//注册动态多数据源
@MapperScan("com.lixy.boothigh.dao")
@SpringBootApplication
@EnableSwagger2/*swagger2启动*/
public class BootHighApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootHighApplication.class, args);
	}

	/**
	 * 文件上传临时路径
	 * 在Spring Boot下配置location
	 * 解决linux下报The temporary upload location [/tmp/tomcat.135042057.80/work/Tomcat/localhost/ROOT] is not valid
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//默认为linux
		String location = BConstant.TMP_LINUX;
		/**
		 * 判断是否为window
		 */
		if (System.getProperty(BConstant.SYS_PROPERTY_OS_NAME).toLowerCase().contains(BConstant.SYS_OS_NAME_WINDOW)) {
			location = BConstant.TMP_WINDOWS;

		}
		File file = new File(location);
		if (!file.exists()) {
			file.mkdirs();
		}
		factory.setLocation(location);
		return factory.createMultipartConfig();
	}


}
