package com.lixy.boothigh.utils;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: MR LIS
 * @Description: 读取配置文件信息
 * @Date: 11:10 2018/4/25
 * @return
 */
public class PropertyReaderUtils {

    /** 属性值映射 */
    private static Map<String, String> propertiesMap;

    //初始化属性列表
    static {
        propertiesMap = new HashMap<String, String>();
        try {
            //初始化属性读取文件
            Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
            //读取属性列表
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                propertiesMap.put(keyStr, new String(properties.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
            }
        } catch (Exception e) {
            throw new RuntimeException("initialize properties-reader failed", e);
        }
    }

    /**
     * 获取配置属性值
     *
     * @param proKey 配置属性的键
     * @return String 配置属性的文本值
     */
    public static String getProValue(String proKey) {
        return propertiesMap.get(proKey);
    }
}
