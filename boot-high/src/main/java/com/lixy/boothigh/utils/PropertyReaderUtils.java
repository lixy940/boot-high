package com.lixy.boothigh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class PropertyReaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderUtils.class);

    /**
     * 属性值映射
     */
    private static Map<String, String> propertiesMap;

    private final static PropertyReaderUtils util = new PropertyReaderUtils();

    public PropertyReaderUtils() {
        propertiesMap = new HashMap<>();
        logger.info("********* 初始化配置文件 db.properties***************");
        loadProperties("db.properties");
        logger.info("********* 初始化配置文件 application.properties***************");
        loadProperties("application.properties");
        //如果激活文件设置不为空，则对该文件进行属性封装
        if (propertiesMap.get("spring.profiles.active") != null) {
            loadProperties("application-" + propertiesMap.get("spring.profiles.active").trim() + ".properties");
        }
    }

    /**
     * 加载配置文件
     *
     * @param configName 文件名称
     */
    private static void loadProperties(String configName) {
        File file = null;
        try {
            URL uri = PropertyReaderUtils.class.getClassLoader().getResource("");
            if (uri != null) {
                if (uri.toString().contains("test-classes")) {
                    /**
                     * 测试test用
                     */
                    String realPath = System.getProperty("user.dir");
                    file = new File(realPath + "/target/classes/" + configName);
                } else {
                    file = new File(uri.getPath() + "/" + configName);
                }
            }
        } catch (Exception e) {
            logger.error("load properties error!", e);
        }
        if (file == null || !file.exists()) {
            String realPath = System.getProperty("user.dir");
            file = new File(realPath + "/config/" + configName);
        }
        if (!file.exists()) {
            String jarPath = getProjectPath();
            file = new File(jarPath + "/config/" + configName);
        }
        readFile(file);
    }

    /**
     * 获取jar包执行路径
     *
     * @return
     */
    private static String getProjectPath() {
        URL url = PropertyReaderUtils.class.getProtectionDomain().getCodeSource()
                .getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        File file = new File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    /**
     * 读取文件
     *
     * @param file
     */
    private static void readFile(File file) {
        Properties props = new Properties();
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            props.load(new InputStreamReader(fis, "utf8"));
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                propertiesMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("utf-8")));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static PropertyReaderUtils getInstance() {
        return util;
    }

    /**
     * 获取配置属性值
     *
     * @param proKey 配置属性的键
     * @return String 配置属性的文本值
     */
    public String getProValue(String proKey) {
        return propertiesMap.get(proKey);
    }

}
