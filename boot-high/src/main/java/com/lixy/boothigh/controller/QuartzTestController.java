package com.lixy.boothigh.controller;

import com.lixy.boothigh.service.TaskService;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * quartz任务测试
 * @author LIS
 * @date 2019/2/21 12:40
 */
@Api(tags = {"quartz计划任务"})
@RequestMapping("/quartz")
@RestController
public class QuartzTestController {

    @Autowired
    private TaskService taskService;


    @ApiOperation(value = "任务同步", notes = "任务同步")
    @GetMapping("synTask")
    public JsonResult synTask(Integer taskId){
        taskService.executeTask(taskId);
        return new JsonResult();
    }
}
