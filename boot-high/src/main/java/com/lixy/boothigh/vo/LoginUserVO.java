package com.lixy.boothigh.vo;

/**
 * Created by Administrator on 2017/9/12.
 * @desc 登录用户vo
 */
public class LoginUserVO extends IdEntityVO {
    /**
     * 用户名
     */
    private String username;
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


    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
