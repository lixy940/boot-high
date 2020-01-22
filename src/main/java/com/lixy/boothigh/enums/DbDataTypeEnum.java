package com.lixy.boothigh.enums;

/**
 * @Author: MR LIS
 * @Description:数据库数据类型，比较宽泛的定义，便于mysql、oracle等的通用
 * @Date: Create in 11:05 2018/5/28
 * @Modified By:
 */
public enum DbDataTypeEnum {

    NUMBER(1,"number"),
    STRING(2,"string"),
    FLOAT(3,"float"),
    DATE(4,"date"),



    ;




    private int code;
    private String type;
     DbDataTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
