package com.lixy.boothigh;

import com.lixy.boothigh.dynamicDataSource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ServletComponentScan
//注册动态多数据源
@Import({DynamicDataSourceRegister.class})
@MapperScan("com.lixy.boothigh.dao")
@SpringBootApplication
@EnableSwagger2/*swagger2启动*/
public class BootHighApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootHighApplication.class, args);
	}
}
