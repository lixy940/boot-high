package com.lixy.boothigh.enums;

/**
 * Created by  on 2018/5/23.
 * 数据类型
 */
public enum SourceDataTypeEnum {


    EXCEL(1,"excel"),
    CSV(2,"csv"),

    Reult(10,"result"); //数据结果


    private int code;
    private String name;

    SourceDataTypeEnum(int code, String name){
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
