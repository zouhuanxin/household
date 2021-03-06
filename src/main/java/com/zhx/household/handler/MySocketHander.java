package com.zhx.household.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhx.household.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 相关识别功能还不能部署到1核2G服务器使用
 */
@Component
public class MySocketHander implements WebSocketHandler {
    //@Autowired
    //private FaceService faceService;

    /**
     * 为了保存在线用户信息，在方法中新建一个list存储一下【实际项目依据复杂度，可以存储到数据库或者缓存】
     */
    private final static List<WebSocketSession> SESSIONS = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //检查是否存在相同的用户
        for (WebSocketSession user : SESSIONS) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(webSocketSession.getAttributes().get("WEBSOCKET_USERNAME"))) {
                //存在相同的直接删除
                System.out.println("删除重复用户" + user.getAttributes().get("WEBSOCKET_USERNAME") + "......");
                SESSIONS.remove(user);
                break;
            }
        }
        System.out.println("链接成功......");
        SESSIONS.add(webSocketSession);
        // String userName = (String) webSocketSession.getAttributes().get("WEBSOCKET_USERNAME");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject msg = JSON.parseObject(webSocketMessage.getPayload().toString());
        JSONObject obj = new JSONObject();
        if (msg.getInteger("type") == 1) {
            //给所有人

            obj.put("msg", msg.getString("msg"));
            sendMessageToUsers(new TextMessage(obj.toJSONString()));
        } else if (msg.getInteger("type") == 2) {
            //转发给个人
            String to = msg.getString("to");
            obj.put("msg", msg.getJSONObject("msg"));
            sendMessageToUser(to, new TextMessage(obj.toJSONString()));
        } else {
            //给个人
//                    String to = msg.getString("to");
//                    obj.put("msg", faceService.IdentityFaceInfo(msg.getString("msg")));
//                    sendMessageToUser(to, new TextMessage(obj.toJSONString()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        System.out.println("链接出错，关闭链接......:");
        SESSIONS.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("链接关闭......" + closeStatus.toString());
        SESSIONS.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : SESSIONS) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : SESSIONS) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
