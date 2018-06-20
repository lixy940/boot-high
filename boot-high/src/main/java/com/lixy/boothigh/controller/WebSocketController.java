package com.lixy.boothigh.controller;

import com.lixy.boothigh.vo.page.JsonResult;
import com.lixy.boothigh.websocket.WebSocketEndPoint;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 14:01 2018/6/20
 * @Modified By:
 */
@Api(tags = {"webSocket长连接"})
@Controller
@RequestMapping("/socktest")
public class WebSocketController {

    /**
     * 长连接主页
     * @return
     */
    @ApiOperation(value = "websocket长连接", notes = "websocket长连接")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/index")
    public String index(){
        return "websocket";
    }


    @Autowired
    private WebSocketEndPoint webSocket;

    /**
     * 模拟接受消息
     */
    @RequestMapping("/receive")
    @ResponseBody
    public void receiveMessage(){
        //服务器发送消息到客户端
        webSocket.sendMessage("aha, i send this message");
    }
}
