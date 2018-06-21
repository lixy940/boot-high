
  
package com.lixy.boothigh.runner;

import com.lixy.boothigh.service.RedisService;
import com.lixy.boothigh.thread.ExcelSynThread;
import com.lixy.boothigh.thread.MonitorTaskHandlerThread;
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
    @Autowired
    private MonitorTaskHandlerThread handlerThread;
	@Override
	public void run(String... args){
		logger.info(this.getClass().getName() + "启动加载数据********");
		//启动excel文件服务同步任务
		new Thread(excelSynThread).start();
		logger.info("********excel文件服务同步任务启动完成********");
        new Thread(handlerThread).start();
        logger.info("********数据同步任务监控启动完成********");
	}
}
  
