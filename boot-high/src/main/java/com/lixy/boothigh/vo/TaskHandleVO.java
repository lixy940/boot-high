package com.lixy.boothigh.vo;

import java.io.Serializable;

/**
 * @Author: MR LIS
 * @Description:excel模板数据导入时的任务状态
 * @Date: Create in 14:02 2018/6/14
 * @Modified By:
 */
public class TaskHandleVO implements Serializable{

    /**
     * 同步到es/neo4j是否完成
     */
    private boolean esFlag;
    /**
     * 同步到tidb数据库是否完成
     */
    private boolean tiFlag;
    /**
     *上传附件id
     */
    private Integer uploadPathId;

    public TaskHandleVO(Integer uploadPathId) {
        this.uploadPathId = uploadPathId;
    }

    public boolean isEsFlag() {
        return esFlag;
    }

    public void setEsFlag(boolean esFlag) {
        this.esFlag = esFlag;
    }

    public boolean isTiFlag() {
        return tiFlag;
    }

    public void setTiFlag(boolean tiFlag) {
        this.tiFlag = tiFlag;
    }

    public Integer getUploadPathId() {
        return uploadPathId;
    }

    public void setUploadPathId(Integer uploadPathId) {
        this.uploadPathId = uploadPathId;
    }
}
