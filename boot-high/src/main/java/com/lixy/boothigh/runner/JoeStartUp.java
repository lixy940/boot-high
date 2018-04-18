/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:XbqStartUp.java  
 * Package Name:com.lixy.runner
 * Author   Joe
 * Date:2017年11月6日下午7:46:48
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: 17:01 2018/4/18
 * @return
 */
@Component
@Order(value = 2)
public class JoeStartUp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(JoeStartUp.class);

	@Override
	public void run(String... args) throws Exception {
		logger.info(this.getClass().getName() + "启动加载数据********" + args);
	}
}
  
