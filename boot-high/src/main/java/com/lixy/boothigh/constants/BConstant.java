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

    //======================当前用户key===================================================================================

    /**
     * excel上传文件目录
     */
    public static final String EXCEL_UPLOAD_DIR = PropertyReaderUtils.getProValue("excel.upload.dir");

    /**
     * webapp 根目录的路径
     */
    public static final String WEBAPP_ROOT_PATH = PropertyReaderUtils.getProValue("webapp.root.path");


}
