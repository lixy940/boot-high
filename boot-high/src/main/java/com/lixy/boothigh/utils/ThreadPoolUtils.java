package com.lixy.boothigh.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 统一线程池
 *
 * @param
 * @author silent
 * @date 2018/7/10
 * @return
 */
public class ThreadPoolUtils {

    /**
     * IO密集型线程池
     *
     * @author silent
     * @date 2018/7/10
     * @param
     * @return
     */
    private static final String IO_THREAD_POOL_NAME = "io-pool-%d";
    private static final int IO_THREAD_POOL_THREADS = Runtime.getRuntime().availableProcessors() * 50;
    public static final ExecutorService IO_THREAD_POOL = new ThreadPoolExecutor(
            IO_THREAD_POOL_THREADS,
            IO_THREAD_POOL_THREADS,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat(IO_THREAD_POOL_NAME).build());


    /**
     * 单线程线程池
     *
     * @author silent
     * @date 2018/7/10
     * @param
     * @return
     */
    private static final String SINGLE_THREAD_POOL_NAME = "single-pool-%d";
    public static final ExecutorService SINGLE_THREAD_POOL = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat(SINGLE_THREAD_POOL_NAME).build());

}
