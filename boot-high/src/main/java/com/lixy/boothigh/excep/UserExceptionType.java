package com.lixy.boothigh.excep;

/**
 * 用户拦截异常类型，还可以定义其他对象，用于对应业务的异常枚举
 */
public enum UserExceptionType implements ExceptionType {

    LOGIN_INFO_IS_ERROR(1000,"login info is invalid,please login again"),
    USER_IS_NOT_EXIST(1001,"user is not exist"),
    PASSWORD_IS_ERROR(1002,"password or username error"),
    USERNAME_IS_EXIST(1003,"username already exist"),
    USERNAME_OR_PASSWORD_IS_MULL(1004,"username or passord is null"),
    ROLENAME_IS_EXIST(1005,"rolename already exist"),
    CAN_NOT_DELETE(1006,"There are associated users,can not delete"),
    ROLE_IS_NOT_EXIST(1007,"role is not exist"),
    IDCARD_IS_NOT_EXIST(1008,"idcard is not exist,please set it"),
    USER_ORG_CODE_IS_EXIST(1009, "organization code already exist"),
    USER_ORG_IS_PARENT(1010, "not allowed delete,the organization has some subordinate units"),
    USER_ORG_HAS_USER(1011, "not allowed delete,the organization has some users")
    ;


    UserExceptionType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常描述
     */
    private String description;

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
