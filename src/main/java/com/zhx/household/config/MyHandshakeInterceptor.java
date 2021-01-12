package com.zhx.household.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * 重写方法 在握手之前做的事情
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
            // 从session中获取到当前登录的用户信息. 作为socket的账号信息. session的的WEBSOCKET_USERNAME信息,在用户打开页面的时候设置.
            //String userName = (String) servletRequest.getSession().getAttribute("WEBSOCKET_USERNAME");
            String userName = servletRequest.getQueryString().split("=")[1];
            map.put("WEBSOCKET_USERNAME", userName);
        }
        return true;
    }

    /**
     * 重写方法 在握手之后做的事情
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
