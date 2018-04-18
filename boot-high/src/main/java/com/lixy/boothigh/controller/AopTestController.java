package com.lixy.boothigh.controller;


import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.bean.Student;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: MR LIS
 * @Description:aop 测试
 * @Date: Create in 15:36 2018/4/18
 * @Modified By:
 */
@Api(tags = {"aop使用接口"})
@RequestMapping("/aop")
@RestController
public class AopTestController {
    /**
     * @Author: MR LIS
     * @Description: aop测试方法
     * @Date: 14:04 2018/4/18
     * @return
     */
    @SystemControllerLog(methodDesc = "aopIndexControllerAop")
    @ApiOperation(value = "aop测试方法", notes = "aop测试方法", response = JsonResult.class)
    @PostMapping("/aopIndex")
    public JsonResult aopIndex(Student student) {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }

}
