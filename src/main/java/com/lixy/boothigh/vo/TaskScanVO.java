package com.lixy.boothigh.vo;

import java.io.Serializable;

/**
 * @Author: MR LIS
 * @Description:任务扫描对象
 * @Date: Create in 10:18 2018/6/19
 * @Modified By:
 */
public class TaskScanVO implements Serializable{

    /**
     *上传附件的id
     */
    private Integer uploadPathId;

    /**
     * 本机ip
     */
    private String ip;

    public TaskScanVO(Integer uploadPathId, String ip) {
        this.uploadPathId = uploadPathId;
        this.ip = ip;
    }

    public Integer getUploadPathId() {
        return uploadPathId;
    }

    public void setUploadPathId(Integer uploadPathId) {
        this.uploadPathId = uploadPathId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
