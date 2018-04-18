package com.lixy.boothigh.excep;

/**
 * Created by Administrator on 2017/9/12
 * @desc 响应结果对象状态码枚举对象
 *   1 返回内容为空 0 成功 -1 服务器错误 -2 访问被拒绝
 *   -3 请求方法错误 -4 请求参数错误 -5 请求版本错误 -6 调用第三方错误
 *   -7 获取锁错误 -8 无权限访问 -9 登录失败(用户名或密码错误) -10 ⽤用户过期或处在无登录状态
 */
public enum ServiceErrorCode {
    NORMAL(0,"成功"),
    SERVER_ERR(-1,"服务器错误:{0}"),
    ACCESS_DENY(-2,"访问被拒绝:{0}"),
    REQUEST_METHOD_ERROR(-3,"请求方法错误:{0}"),
    REQUEST_ARGS_ERROR(-4,"请求参数错误:{0}"),
    REQUEST_VERSION_ERROR(-5,"请求版本错误:{0}"),
    CALL_THIRD_EEEOR(-6,"调用第三方错误:{0}"),
    GET_LOCK_ERROR(-7,"获取锁错误:{0}"),
    NO_AUTH_CALL(-8,"无权限访问:{0}"),
    LOGIN_FAIL(-9,"登录失败(用户名或密码错误):{0}"),
    LOGIN_EXPIRE(-10,"用户过期或处在无登录状态:{0}"),
    ;

    private int code;
    private String msg;

    ServiceErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
