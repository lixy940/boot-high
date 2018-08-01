package com.lixy.boothigh.utils;

import com.lixy.boothigh.constants.BConstant;

/**
 * ftp属性封装
 * @Author: MR LIS
 * @Date: 15:35 2018/8/1
 */
public class FtpAtt {
    /**
     * ip地址
     */
    private String ipAddr;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 目录名称
     */
    private String path;
    public FtpAtt() {
    }

    public FtpAtt(String ipAddr, Integer port, String userName, String pwd, String path) {
        this.ipAddr = ipAddr;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
        this.path = path;
    }

    public static FtpAtt getDefaultConfig(){
        return new FtpAtt(BConstant.FTP_IP, BConstant.FTP_PORT, BConstant.FTP_USERNAME, BConstant.FTP_PASSWORD, BConstant.FTP_DIR);
    }
    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}