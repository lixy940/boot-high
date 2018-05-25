package com.lixy.boothigh.enums;

/**
 * 数据库类型 用于配置数据源连接时判断数据库类型
 */
public enum DBTypeEnum {
    DB_ORACLE("oracle"),
    DB_MYSQL("mysql"),
    DB_POSTGRESQL("postgresql"),
    DB_TIDB("tidb"),
    DB_ES("es");

    private String dbName;

    DBTypeEnum(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
