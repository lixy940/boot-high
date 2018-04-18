package com.lixy.boothigh.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: MR LIS
 * @Description: 配置文件最大上传大小
 * @Date: 17:07 2018/4/18
 * @return
 */
@Configuration
public class CommonConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10240L * 10240L);
        return factory.createMultipartConfig();
    }

}