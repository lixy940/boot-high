/**  
 * ---------------------------------------------------------------------------
 * Copyright (c) 2017, lixy- All Rights Reserved.
 * Project Name:spring-boot-learn  
 * File Name:DynamicDataSourceContextHolder.java  
 * Package Name:com.lixy.dynamicDataSource
 * Author   Joe
 * Date:2017年11月13日下午7:41:49
 * ---------------------------------------------------------------------------  
*/  
  
package com.lixy.boothigh.dynamicDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:DynamicDataSourceContextHolder 
 * Date:     2017年11月13日 下午7:41:49
 * @author   Joe  
 * @version    
 * @since    JDK 1.8
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<String>();
    
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }
    
    public static String getDataSourceType() {
        return contextHolder.get();
    }
    
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
    
    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
