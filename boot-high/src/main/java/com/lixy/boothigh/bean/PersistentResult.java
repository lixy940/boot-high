package com.lixy.boothigh.bean;

/**
 * @Author: MR LIS
 * @Description:数据持久化结果对象
 * @Date: Create in 10:33 2018/5/24
 * @Modified By:
 */
public class PersistentResult {
    /**
     * 主键id
     */
    private Integer resultId;
    /**
     * 表的中文名（数据结果中文名）
     */
    private String resultCname;
    /**
     * 表的英文名（数据结果英文名）
     */
    private String resultEname;
    /**
     * 表的记录数
     */
    private int rowNum;
    /**
     * 关联沙盘数控配置id
     */
    private Integer dbId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createPersonId;

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public String getResultCname() {
        return resultCname;
    }

    public void setResultCname(String resultCname) {
        this.resultCname = resultCname;
    }

    public String getResultEname() {
        return resultEname;
    }

    public void setResultEname(String resultEname) {
        this.resultEname = resultEname;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
