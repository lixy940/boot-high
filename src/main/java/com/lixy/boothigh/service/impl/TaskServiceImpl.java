package com.lixy.boothigh.service.impl;

import com.lixy.boothigh.quartz.QuartzUtils;
import com.lixy.boothigh.quartz.job.MissionJobImpl;
import com.lixy.boothigh.service.TaskService;
import com.lixy.boothigh.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIS
 * @date 2019/2/20 18:00
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static String GROUP_NAME = "boothigh";

    private static final String JOB_NAME_PREFIX = "task_";
    @Autowired
    private QuartzUtils quartzUtils;
    @Override
    public void executeTask(Integer taskId) {
        //写死触发器表达式
        String cron = "30 * * * * ? *";
        //根据任务id获取对应的数据id，必须时唯一的,用作计划的name
        String dataId = "1111"+taskId;
        //放入计划任务中
        Map<String, Object> params = new HashMap<>();
        params.put("dataId", dataId);
        quartzUtils.addJob(JOB_NAME_PREFIX+dataId,GROUP_NAME, cron, MissionJobImpl.class, params);
    }

    @Override
    public void syncData(Integer dataId) {
       //执行逻辑........
        logger.info("处理{}的逻辑", dataId);
        //线程池的使用
        ThreadPoolUtils.executeByIoPool(() -> loadData(dataId));
    }

    /**
     * 只为线程池测试使用而写方法
     * @param dataId
     */
    private void loadData(Integer dataId){
        //.............
    }
}
