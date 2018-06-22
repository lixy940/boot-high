package com.lixy.boothigh.websocket;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 10:02 2018/6/21
 * @Modified By:
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@Component
@ServerEndpoint("/webSocket/{userNo}")
public class WebSocketEndPoint {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketEndPoint.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocketEndPoint> webSocketSet = new ConcurrentHashMap<String, WebSocketEndPoint>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketsession;
    //当前发消息的人员编号，如果一个都没连接时，设置为服务器
    private String userNo = "服务器";

    /**
     * 连接建立成功调用的方法
     *
     * @param：可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userNo") String param, Session WebSocketsession, EndpointConfig config) {
        logger.info("建立连接的用户userNo=[{}]", param);
        userNo = param;//接收到发送消息的人员编号
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(param, this);//加入map中
        addOnlineCount();           //在线数加1
        logger.info("【websocket消息】有新的连接, 当前在线人数总数:{}", getOnlineCount());
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userNo.equals("")) {
            webSocketSet.remove(userNo);  //从set中删除
            subOnlineCount();           //在线数减1
            logger.info("【websocket消息】连接断开, 总数:{}", getOnlineCount());
        }
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @SuppressWarnings("unused")
	@OnMessage
    public void onMessage(String message, Session session) {
        logger.info("【websocket消息】收到客户端[{}]发来的消息:{}", userNo,message);
        // todo 从客户端获取到信息，根据情况并发送给指定人
        sendToUser(message);
/*        if (1 < 2) {
            sendAll(message);
        } else {
            //给指定的人发消息
            sendToUser(message);
        }*/
    }


    /**
     * 给指定的人发送消息,并返回是否通知成功
     *
     * @param message
     */
    public boolean sendToUser(String message) {
        boolean flag=true;
        String[] sArr = message.split("\\|");
        String toUserNo = sArr[1];
        String sendMessage = sArr[0];
        String now = getNowTime();
        try {
            if (webSocketSet.get(toUserNo) != null) {
                webSocketSet.get(toUserNo).sendMessage(now + " 用户【" + userNo + "】发来消息："  + sendMessage);
            } else {
                logger.info("当前用户userNo={}不在线", toUserNo);
                flag=false;
            }
        } catch (IOException e) {
            flag=false;
            e.printStackTrace();
        }finally {
            return flag;
        }
    }


    /**
     * 给所有人发消息
     *
     * @param message
     */
    private void sendAll(String message) {
        String now = getNowTime();
        String sendMessage = message.split("\\|")[0];
        //遍历HashMap
        for (String key : webSocketSet.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userNo.equals(key)) {
                    webSocketSet.get(key).sendMessage(now + " 用户【" + userNo + "】发来消息：" + sendMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("【websocket消息】发生错误:");
        error.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        WebSocketEndPoint.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        WebSocketEndPoint.onlineCount--;
    }
}

