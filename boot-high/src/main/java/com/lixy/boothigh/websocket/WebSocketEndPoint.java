package com.lixy.boothigh.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: MR LIS
 * @Description:
 * @Date: Create in 16:23 2018/6/20
 * @Modified By:
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSocketEndPoint {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketEndPoint.class);

    private Session session;

    private static CopyOnWriteArraySet<WebSocketEndPoint> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        logger.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        logger.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    /**
     * 发送所有
     * @param message
     */
    public void sendMessage(String message) {
        for (WebSocketEndPoint webSocket: webSocketSet) {
            logger.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(String message) {
        for (WebSocketEndPoint webSocket: webSocketSet) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param message
     */
    public void sendMessageToUser(String message) {

    }

}