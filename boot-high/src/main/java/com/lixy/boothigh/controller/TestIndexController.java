package com.lixy.boothigh.controller;

import com.lixy.boothigh.aop.SystemControllerLog;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 11:18 2018/4/19
 * @Modified By:
 */
@Api(tags = {"test使用接口"})
@RequestMapping("/test")
@RestController
public class TestIndexController {

    /**
     * @Author: MR LIS
     * @Description: aop测试方法
     * @Date: 14:04 2018/4/18
     * @return
     */
    @SystemControllerLog(methodDesc = "aopIndexControllerAop")
    @GetMapping("/aopIndex")
    public JsonResult aopIndex(String username,String password) {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }


    /**
     * @Author: MR LIS
     * @Description: swagger测试方法 使用详情参考:https://blog.csdn.net/molashaonian/article/details/72998428
     * @Date: 11:04 2018/4/19
     * @return
     */
    @ApiOperation(value = "swagger测试方法", notes = "swagger测试方法", response = JsonResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue="admin"),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="123456"),
            @ApiImplicitParam(paramType="path",name="phone",dataType="String",required=true,value="手机号",defaultValue="")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/swagger/{phone}")
    public JsonResult swaggerMethod(@RequestParam("username") String username, @RequestParam("password") String password, @PathVariable("phone")String phone) {
        JsonResult jsonResult = new JsonResult();
        return jsonResult;
    }


    /**
     * post方法参数放入body的获取方式
     * @param request
     */
    @ApiOperation(value = "post方法参数放入body的获取方式", notes = "post方法参数放入body的获取方式", response = JsonResult.class)
    @PostMapping("/requestBodyMethod")
    public void requestBodyMethod(HttpServletRequest request) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String str = "";
            String wholeStr = "";
            //一行一行的读取body体里面的内容；
            while((str = reader.readLine()) != null){
                wholeStr += str;
            }
            System.out.println("requestBody："+wholeStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
