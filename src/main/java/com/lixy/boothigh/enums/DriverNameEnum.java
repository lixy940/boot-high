package com.lixy.boothigh.enums;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 17:37 2018/5/24
 * @Modified By:
 */
public enum DriverNameEnum {

    DRIVER_MYSQL("com.mysql.jdbc.Driver"),
    DRIVER_ORACLE("oracle.jdbc.driver.OracleDriver"),
    DRIVER_POSTGRES("org.postgresql.Driver"),

    ;

    private String driverName;

    DriverNameEnum(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
