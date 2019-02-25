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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @author LIS
 */
@Component
@ServerEndpoint("/webSocket/{uniqueKey}")
public class WebSocketEndPoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketEndPoint.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static List<WebSocketEndPoint> webSocketEndPoints = new CopyOnWriteArrayList<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session webSocketsession;

    /**
     * 当前发消息的人员编号，如果一个都没连接时，设置为服务器
     */
    private String uniqueKey;

    /**
     * 连接建立成功调用的方法
     *
     * @param：可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "uniqueKey") String param,Session webSocketsession) {
        LOGGER.info("建立连接的用户userNo=[{}]", param);
        //接收到发送消息的人员编号
        uniqueKey = param;
        this.webSocketsession = webSocketsession;
        webSocketEndPoints.add(this);
        LOGGER.info("【websocket消息】有新的连接, 当前在线人数总数:{}", getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketEndPoints.remove(this);
        LOGGER.info("【websocket消息】连接断开, 总数:{}", getOnlineCount());
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
        LOGGER.info("【websocket消息】收到客户端[{}]发来的消息:{}", uniqueKey, message);
        heartBeatMessage();
    }

    /**
     * 给所有人发消息
     *
     * @param message
     */
    public void sendMessageToAll(String message) throws IOException, EncodeException {
        for (WebSocketEndPoint webSocketEndPoint : webSocketEndPoints) {
            webSocketEndPoint.webSocketsession.getBasicRemote().sendText(message);
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.info("【websocket消息】发生错误:");
        error.printStackTrace();
    }

    /**
     * 心跳检测
     *
     * @throws IOException
     */
    public void heartBeatMessage() {
        try {
            this.webSocketsession.getBasicRemote().sendText("websocket is beating");
        } catch (Exception e) {
            LOGGER.error("ws heartBeatMessage error", e);
        }
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) {
        try {
            this.webSocketsession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            LOGGER.error("ws sendMessage error", e);
        }
    }


    private int getOnlineCount() {
        return webSocketEndPoints.size();
    }


}

