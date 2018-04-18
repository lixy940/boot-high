package com.lixy.boothigh.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 13:47 2018/4/10
 * @Modified By:
 */
@Configuration
public class TomcatConfig {
    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        ConfigurableEmbeddedServletContainer factory = new TomcatEmbeddedServletContainerFactory();
//        factory.setDocumentRoot(new File(BConstant.WEBAPP_ROOT_PATH));
        return (EmbeddedServletContainerFactory) factory;
    }
}