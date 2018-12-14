package com.lixy.boothigh.vo;

import java.util.List;

/**
 * @author LIS
 * @date 2018/12/1416:41
 */
public class ConditionCountVo {
    /**
     * 数据库id
     */
    private Integer dbId;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 条件集合
     */
    private List<ConditionVo> conditionVos;

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ConditionVo> getConditionVos() {
        return conditionVos;
    }

    public void setConditionVos(List<ConditionVo> conditionVos) {
        this.conditionVos = conditionVos;
    }
}
