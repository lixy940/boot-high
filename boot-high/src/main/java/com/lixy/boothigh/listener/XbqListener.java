/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:springboot-test  
 * File Name:JoeListener.java  
 * Package Name:com.lixy.listener
 * Author   Joe
 * Date:2017年11月6日下午6:28:29
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**  
 * ClassName:JoeListener 
 * Date:     2017年11月6日 下午6:28:29
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class XbqListener implements ServletContextListener{
	
	private static Logger logger = LoggerFactory.getLogger(XbqListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("--xbq-监听器-ServletContext 初始化");
		logger.info(sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("--xbq-监听器-ServletContext 销毁");
	}
}
  
