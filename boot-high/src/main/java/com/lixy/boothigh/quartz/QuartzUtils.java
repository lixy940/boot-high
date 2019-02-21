package com.lixy.boothigh.quartz;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Quartz工具类
 * Job和Trigger的key（JobKey和TriggerKey）可以用于将Job和Trigger放到不同的分组（group）里，然后基于分组进行操作。
 * 同一个分组下的Job或Trigger的名称必须唯一，即一个Job或Trigger的key由名称（name）和分组（group）组成。
 * 如果group为null,默认为放到DEFAULT_GROUP=DEFAULT组里
 */
@Component
public class QuartzUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzUtils.class);

    @Autowired
    private Scheduler scheduler;


    /**
     * 新增任务
     * @param name 名称
     * @param groupName 组
     * @param cron 触发器表达式
     * @param jobClass 工作的类
     * @param params 参数
     * @return
     */
    public boolean addJob(String name,String groupName, String cron, Class<? extends Job> jobClass, Map<String, Object> params) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(cron) || null == jobClass) {
            return false;
        }

        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(name, groupName)).build();
            if (null != params && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
                }
            }

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(name, groupName))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing())
                    .forJob(getJobKey(name, groupName))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (Exception e) {
            LOGGER.error("新建任务失败, name: {},groupName:{}", name,groupName, e);
            return false;
        }
    }


    /**
     * 更新cron
     *
     * @param
     * @return
     */
    public boolean updateCron(String name,String groupName, String cron) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(cron)) {
            return false;
        }

        try {
            CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(getTriggerKey(name,groupName));
            trigger.setCronExpression(new CronExpression(cron));
            scheduler.rescheduleJob(getTriggerKey(name,groupName), trigger);
            return true;
        } catch (Exception e) {
            LOGGER.error("更新任务时间失败, name: {},groupName:{}", name,groupName, e);
            return false;
        }
    }


    /**
     * 删除任务
     *
     * @param
     * @return
     */
    public boolean deleteJob(String name,String groupName) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        try {
            scheduler.deleteJob(getJobKey(name,groupName));
            return true;
        } catch (Exception e) {
            LOGGER.error("删除任务失败, name: {},groupName:{}", name, groupName, e);
            return false;
        }
    }


    /**
     * 恢复所有错误的触发器
     * @param
     * @return
     */
    public void restoreAllErrorTriggers() {
        try {
            List<String> jobGroupNames = scheduler.getJobGroupNames();
            if (CollectionUtils.isEmpty(jobGroupNames)) {
                return;
            }

            for (String jobGroupName : jobGroupNames) {
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName));
                if (CollectionUtils.isEmpty(jobKeys)) {
                    continue;
                }
                for (JobKey jobKey : jobKeys) {
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    if (CollectionUtils.isEmpty(triggers)) {
                        continue;
                    }
                    for (Trigger trigger : triggers) {
                        TriggerKey triggerKey = trigger.getKey();
                        if (Trigger.TriggerState.ERROR.equals(scheduler.getTriggerState(triggerKey))) {
                            scheduler.resumeTrigger(triggerKey);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("恢复所有错误的触发器失败", e);
        }
    }


    /**
     * job key
     *
     * @param
     * @return
     */
    private JobKey getJobKey(String name,String groupName) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return new JobKey(name, groupName);
    }


    /**
     * trigger key
     * @param
     * @return
     */
    private TriggerKey getTriggerKey(String name,String groupName) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return new TriggerKey(name, groupName);
    }

}
