/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, xsjt- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:XbqStartUp.java  
 * Package Name:com.xsjt.runner
 * Author   Joe
 * Date:2017年11月6日下午7:46:48
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.runner;

import com.lixy.boothigh.service.RedisService;
import com.lixy.boothigh.thread.ExcelSynThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: MR LIS
 * @Description: 启动时加载服务
 * @Date: 16:38 2018/4/13
 * @return
 */
@Component
@Order(value = 2)
public class InitStartUp implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(InitStartUp.class);
	@Autowired
	private RedisService redisService;
	@Autowired
	private ExcelSynThread excelSynThread;
	@Override
	public void run(String... args){
		logger.info(this.getClass().getName() + "启动加载数据********");
/*		try {
			sysCategoryService.initDataElementToRedis();
		} catch (ServiceException e) {
			logger.error("数据元素id->regex初始化完成异常");
		}*/
		logger.info("********数据元素id->regex初始化完成********");
		//启动excel文件服务同步任务
		new Thread(excelSynThread).start();
		logger.info("********excel文件服务同步任务启动完成********");
	}
}
  
