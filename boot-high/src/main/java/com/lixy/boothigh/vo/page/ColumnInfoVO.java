package com.lixy.boothigh.vo.page;

/**
 * 数据库列的属性、注释、数据类型
 *
 */
public class ColumnInfoVO {

    /**
     * 列的名字
     */
    private String columnEname;

    /**
     *
     */
    private String columnCname;

    /**
     *
     */
    private String columnType;

    public ColumnInfoVO() {
    }

    public ColumnInfoVO(String columnEname, String columnCname, String columnType) {
        this.columnEname = columnEname;
        this.columnCname = columnCname;
        this.columnType = columnType;
    }

    public String getColumnEname() {
        return columnEname;
    }

    public void setColumnEname(String columnEname) {
        this.columnEname = columnEname;
    }

    public String getColumnCname() {
        return columnCname;
    }

    public void setColumnCname(String columnCname) {
        this.columnCname = columnCname;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
