package com.lixy.boothigh.bean;

/**
 * @Author: MR LIS
 * @Description:数据库连接配置对象
 * @Date: Create in 10:26 2018/5/24
 * @Modified By:
 */
public class DataBaseConfig {
    /**
     * 数据库id 主键id
     */
    private Integer dbId;
    /**
     * 数据库名字
     */
    private String dbName;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 数据库id
     */
    private String dbIp;
    /**
     * 数据库端口
     */
    private String dbPort;
    /**
     * 服务名称
     */
    private String dbServerName;
    /**
     * 其他字段，postgres中同一个库分为不同的模式
     */
    private String dbTableSchema;
    /**
     * postgresql库区分视图与表 r:表 v:视图
     */
    private String dbRelkind;
    /**
     * 用户名
     */
    private String dbUser;
    /**
     * 密码
     */
    private String dbPassword;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createPersonId;


    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbServerName() {
        return dbServerName;
    }

    public void setDbServerName(String dbServerName) {
        this.dbServerName = dbServerName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getDbTableSchema() {
        return dbTableSchema;
    }

    public void setDbTableSchema(String dbTableSchema) {
        this.dbTableSchema = dbTableSchema;
    }

    public String getDbRelkind() {
        return dbRelkind;
    }

    public void setDbRelkind(String dbRelkind) {
        this.dbRelkind = dbRelkind;
    }
}
