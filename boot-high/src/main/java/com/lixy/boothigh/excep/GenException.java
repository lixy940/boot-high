package com.lixy.boothigh.excep;

/**
 * @author yuanyang
 * 业务异常顶层接口
 * @date 2018/5/29 16:30
 */
public interface GenException {

    /**
     * 异常码
     */
    int getCode();

    /**
     * 异常描述
     */
    String getDescription();
}
