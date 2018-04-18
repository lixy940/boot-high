/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:spring-boot-learn  
 * File Name:DynamicDataSource.java  
 * Package Name:com.lixy.dynamicDataSource
 * Author   Joe
 * Date:2017年11月13日下午7:49:49
 * ---------------------------------------------------------------------------  
*/  
package com.lixy.boothigh.dynamicDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ClassName:DynamicDataSource 
 * 继承Spring AbstractRoutingDataSource实现路由切换
 * Date:     2017年11月13日 下午7:49:49
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
