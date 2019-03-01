package com.lixy.boothigh.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jdk.management.resource.internal.FutureWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 统一线程池
 *
 * @param
 * @author silent
 * @date 2018/7/10
 * @return
 */
public class ThreadPoolUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtils.class);

    /**
     * IO密集型线程池
     *
     * @author silent
     * @date 2018/7/10
     * @param
     * @return
     */
    private static final String IO_THREAD_POOL_NAME = "io-pool-%d";
    private static final int IO_THREAD_POOL_THREADS = Runtime.getRuntime().availableProcessors() * 100;
    private static final ExecutorService IO_THREAD_POOL = new ThreadPoolExecutor(
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
    private static final ExecutorService SINGLE_THREAD_POOL = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat(SINGLE_THREAD_POOL_NAME).build());


    /**
     * 提交任务到线程池
     *
     * @param
     * @return
     * @author silent
     * @date 2018/12/7
     */
    public static <T> FutureWrapper<T> submitToIoPool(Callable<T> task) {
        return new FutureWrapper<>(IO_THREAD_POOL.submit(task));
    }


    /**
     * 提交任务到线程池
     *
     * @param
     * @return
     * @author silent
     * @date 2018/12/7
     */
    public static void executeByIoPool(Runnable runnable) {
        IO_THREAD_POOL.submit(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                LOGGER.error("线程执行错误", e);
            }
        });
    }


    /**
     * 提交任务到线程池
     *
     * @param
     * @return
     * @author silent
     * @date 2018/12/7
     */
    public static void executeBySinglePool(Runnable runnable) {
        SINGLE_THREAD_POOL.submit(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                LOGGER.error("线程执行错误", e);
            }
        });
    }

}
