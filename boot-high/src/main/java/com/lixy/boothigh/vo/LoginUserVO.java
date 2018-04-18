package com.lixy.boothigh.vo;

/**
 * Created by Administrator on 2017/9/12.
 * @desc 登录用户vo
 */
public class LoginUserVO extends IdEntityVO {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 手机验证码
     */
    private String verificationCode;
    /**
     * 图片验证码
     */
    private String imgCode;
    /***
     * 密码
     */
    private String password;
    /**
     * droid、ios、wechat、pc
     */
    private String clientSource;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 经纬度
     */
    private String position;
    /**
     * 系统版本
     */
    private String systemVersion;
    /**
     * app版本
     */
    private String appVersion;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientSource() {
        return clientSource;
    }

    public void setClientSource(String clientSource) {
        this.clientSource = clientSource;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }
}
