package com.lixy.boothigh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Future装饰类
 *
 * @param
 * @author silent
 * @date 2018/8/17
 * @return
 */
public class FutureWrapper<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureWrapper.class);

    private Future<V> future;

    private FutureWrapper() {
    }

    public FutureWrapper(Future<V> future) {
        this.future = future;
    }

    /**
     * 安全获取数据
     *
     * @param
     * @return
     * @author silent
     * @date 2018/8/17
     */
    public V getSafety() {
        try {
            return this.future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException e) {
            LOGGER.error("任务执行异常", e);
            return null;
        }
    }
}
