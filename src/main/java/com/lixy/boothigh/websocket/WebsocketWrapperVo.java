package com.lixy.boothigh.websocket;

import java.io.Serializable;

/**
 * @author LIS
 * @date 2019/2/25 16:28
 */
public class  WebsocketWrapperVo<T extends  Serializable> {

    private Integer type;

    private T object;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public WebsocketWrapperVo(Integer type) {
        this.type = type;
    }

    public WebsocketWrapperVo(Integer type, T object) {
        this.type = type;
        this.object = object;
    }

    public WebsocketWrapperVo() {
    }
}
