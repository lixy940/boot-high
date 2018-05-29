package com.lixy.boothigh.enums;

/**
 * 数据类型
 *
 */
public enum SourceDataAreaTypeEnum {


    Uploade(1,"离线数据表"),
    Special(2,"专题数据库"),
    Local(3,"本地数据库"),
    Result(10,"数据结果");

    private int code;
    private String name;

    SourceDataAreaTypeEnum(int code, String name){
        this.code = code;
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public  int getCode(){
        return code;
    }
}
