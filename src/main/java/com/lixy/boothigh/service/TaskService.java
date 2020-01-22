package com.lixy.boothigh.service;

/**
 * @author LIS
 * @date 2019/2/20 17:59
 */
public interface TaskService {
    /**
     * 执行任务
     */
    void executeTask(Integer taskId);

    /**
     * 更加任务id,同步对应的数据
     * @param dataId
     */
    void syncData(Integer dataId);
}
