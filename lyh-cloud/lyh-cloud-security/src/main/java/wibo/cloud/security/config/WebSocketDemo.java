package wibo.cloud.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

/**
 * @Classname WebSocketDemo
 * @Description TODO
 * @Date 2021/2/23 16:14
 * @Created by lyh
 */
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketDemo {

    private static Map<Long, Set<WebSocketDemo>> userSocket = new HashMap<>();

    private Session session;

    private Long userId;

    @OnOpen
    public void onOpen(@PathParam("userId") Long userId, Session session) {
        System.out.println(userId);
        this.session = session;
        this.userId = userId;
        if (userSocket.containsKey(userId)) {
            userSocket.get(userId).add(this);
        } else {
            Set<WebSocketDemo> socketDemos = new HashSet<>();
            socketDemos.add(this);
            userSocket.put(userId, socketDemos);
        }
    }

    @OnClose
    public void close() {
        if (userSocket.get(this.userId).size() == 0) {
            userSocket.remove(this.userId);
        } else {
            userSocket.get(this.userId).remove(this);
            if (userSocket.get(this.userId).size() == 0) {
                userSocket.remove(this.userId);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public Boolean sendMessageToUser(Long userId, String message) {
        if (userSocket.containsKey(userId)) {
            for (WebSocketDemo demo : userSocket.get(userId)) {
                try {
                    demo.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
