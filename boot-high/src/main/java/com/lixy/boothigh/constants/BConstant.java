package com.lixy.boothigh.constants;


import com.lixy.boothigh.utils.PropertyReaderUtils;

/**
 * @Author: MR LIS
 * @Description:常见常量
 * @Date: Create in 10:26 2018/3/28
 * @Modified By:
 */
public class BConstant {

    //======================当前用户key===================================================================================

    public final static String CURRENT_USER_KEY = "user";
    //login User cookie key
    public final static String COOKIE_USER_KEY = "COOKIE_USER_KEY";

    //======================当前用户key===================================================================================

    /**
     * excel上传文件目录
     */
    public static final String EXCEL_UPLOAD_DIR = PropertyReaderUtils.getProValue("excel.upload.dir");

    /**
     * webapp 根目录的路径
     */
    public static final String WEBAPP_ROOT_PATH = PropertyReaderUtils.getProValue("webapp.root.path");
    /**
     * url
     */
    public static final String SPRING_DATASOURCE_URL = PropertyReaderUtils.getProValue("spring.datasource.url");
    /**
     * username
     */
    public static final String SPRING_DATASOURCE_USERNAME = PropertyReaderUtils.getProValue("spring.datasource.username");
    /**
     * password
     */
    public static final String SPRING_DATASOURCE_PASSWORD = PropertyReaderUtils.getProValue("spring.datasource.password");
    //==========================redis 上传excel任务key==================================================================
    /**
     * 任务扫描
     */
    public static final String TEMPLATE_REDIS_TASK_SCAN_KEY = "TEMPLATE_REDIS_TASK_SCAN_KEY";
    /**
     * 任务处理
     */
    public static final String TEMPLATE_REDIS_HANDLER_KEY = "TEMPLATE_REDIS_HANDLER_KEY_";
    //==========================redis 上传excel任务key==================================================================

    /**
     * 过期数量
     */
    public static final int TEMPLATE_EXPIRE_NUM = 7;
    /**
     * 同步excel一个批次处理的记录数
     */
    public static final int EXCEL_TO_LOCAL_NUM = 50000;
    /**
     * 同步excel一个批次处理的记录数
     */
    public static final int DB_HANDLE_BATCH_NUM=10000;


    /**
     * FTP 登录用户名
     */
    public final static String FTP_USERNAME = PropertyReaderUtils.getProValue("ftp.username");
    /**
     * FTP 登录密码
     */
    public final static String FTP_PASSWORD = PropertyReaderUtils.getProValue("ftp.password");
    /**
     * FTP 服务器地址IP地址
     */
    public final static String FTP_IP = PropertyReaderUtils.getProValue("ftp.ip");
    /**
     * FTP 端口
     */
    public final static int FTP_PORT = Integer.parseInt(PropertyReaderUtils.getProValue("ftp.port"));
    /**
     * FTP指定目录
     */
    public final static String FTP_DIR = PropertyReaderUtils.getProValue("ftp.dir");
}
