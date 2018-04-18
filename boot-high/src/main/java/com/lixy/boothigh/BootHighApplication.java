package com.lixy.boothigh;

import com.lixy.boothigh.dynamicDataSource.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

@ServletComponentScan
//注册动态多数据源
@Import({DynamicDataSourceRegister.class})
@MapperScan("com.lixy.boothigh.dao")
@SpringBootApplication
public class BootHighApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootHighApplication.class, args);
	}
}
