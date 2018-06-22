package com.lixy.boothigh.bean;

import java.util.Date;

/**
 * @Author: MR LIS
 * @Description:上传文件路径对象
 * @Date: Create in 14:15 2018/6/5
 * @Modified By:
 */
public class SysFilePath {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 上传文件的存储路径
     */
    private String path;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 业务类型 1 沙盘 2 研判
     */
    private Integer type;
    /**
     * 文件类型
     */
    private Integer templateId;

    /**
     * 上传附件时解析数据
     */
    private String linkTypeJson;
    /**
     * 解析状态 0 待解析 1 已解析
     */
    private Integer status;
    /**
     * 创建人id
     */
    private String createPersonId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getLinkTypeJson() {
        return linkTypeJson;
    }

    public void setLinkTypeJson(String linkTypeJson) {
        this.linkTypeJson = linkTypeJson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }
}
