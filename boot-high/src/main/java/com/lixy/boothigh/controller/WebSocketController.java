package com.lixy.boothigh.controller;

import com.lixy.boothigh.websocket.WebSocketEndPoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: MR LIS
 * @Description:websocket长连接控制层
 * @Date: Create in 14:01 2018/6/20
 * @Modified By:
 */
@Api(tags = {"webSocket长连接"})
@Controller
@RequestMapping("/socktest")
public class WebSocketController {

    /**
     * 长连接主页，不同的会话就是一个用户的连接
     * @return
     */
    @ApiOperation(value = "websocket长连接", notes = "websocket长连接")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/websocket")
    public String websocket(){
        return "websocket";
    }

    /*
    * receive1()、receive2()分别模拟的服务器端向客户端用户1和客户端用户2进行消息推送
    *
    * */


    @Autowired
    private WebSocketEndPoint socketTest;

    @RequestMapping("/receive1")
    @ResponseBody
    public void receive1(){
        String message = "上传任务完成了！|用户1";
        //服务器发送消息到客户端
        socketTest.sendMessage(message);
    }

    @RequestMapping("/receive2")
    @ResponseBody
    public void receive2(){
        String message = "上传任务完成了！|用户2";
        //服务器发送消息到客户端
        socketTest.sendMessage(message);
    }
}
