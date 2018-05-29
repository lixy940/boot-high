package com.lixy.boothigh.vo;

/**
 * Created by  on 2018/5/24.
 * 数据源的基本信息
 */
public class SourceDataInfoVO {

    /**
     * 数据库id
     */
    private Integer dbId;
    /**
     *表名字
     */
    private String tableEname;

    /**
     *  表注释  中文名字
     */
    private String  tableCname;

    /**
     * 数据类型
     * Excel txt 结果 等 小类型
     */
    private int sourceDataType;






    public SourceDataInfoVO() {
    }


    public SourceDataInfoVO(Integer dbId, String tableEname, String tableCname) {
        this.dbId = dbId;
        this.tableEname = tableEname;
        this.tableCname = tableCname;
    }

    public SourceDataInfoVO(Integer dbId, String tableEname, String tableCname, int sourceDataType) {
        this.dbId = dbId;
        this.tableEname = tableEname;
        this.tableCname = tableCname;
        this.sourceDataType = sourceDataType;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getTableEname() {
        return tableEname;
    }

    public void setTableEname(String tableEname) {
        this.tableEname = tableEname;
    }

    public String getTableCname() {
        return tableCname;
    }

    public void setTableCname(String tableCname) {
        this.tableCname = tableCname;
    }

    public int getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(int sourceDataType) {
        this.sourceDataType = sourceDataType;
    }
}
