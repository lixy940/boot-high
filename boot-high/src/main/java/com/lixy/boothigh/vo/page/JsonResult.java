package com.lixy.boothigh.vo.page;

import java.io.Serializable;

/**
 * @Author: MR LIS
 * @Description:自定义返回json数据对象
 * @Date: Create in 9:25 2018/3/29
 * @Modified By:
 */
public class JsonResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private static final String MESSAGE = "成功";


    private int state;
    private String message;
    private T data;
    /**
     * 分页条件
     */
    private Pagination pagination;
    //构造方法
    public JsonResult() {
        state = SUCCESS;
        message = MESSAGE;
    }

    public JsonResult(T data) {
        state = SUCCESS;
        this.data = data;
    }

    public JsonResult(int state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public JsonResult(Throwable e) {
        state = ERROR;
        this.message = e.getMessage();
    }

    //get AND set 方法
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pagination=" + pagination +
                '}';
    }
}