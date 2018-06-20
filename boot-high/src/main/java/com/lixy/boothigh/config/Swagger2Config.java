package com.lixy.boothigh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;

/**
 * @author
 * @Description:swagger配置类
 * @date 2018/3/23 10:55
 */
@Configuration
@Profile({"!release", "!pro", "!master"})
public class Swagger2Config {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.lixy.boothigh.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Timestamp.class, Long.class);
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(" boot-high api docs")
                .version("1.0.0")
                .termsOfServiceUrl("www.baidu.com")
                .license("MR LIS")
                .licenseUrl("www.baidu.com")
                .build();
    }
}
