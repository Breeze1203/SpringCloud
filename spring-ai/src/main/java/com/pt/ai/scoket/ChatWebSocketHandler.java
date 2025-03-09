package com.pt.ai.scoket;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName ChatWebSocketHandler
 * @Author pt
 * @Description WebSocket 处理类，用于实现多人实时聊天功能
 * @Date 2025/3/6 21:24
 **/
@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final OpenAiChatModel chatModel;

    // 使用线程安全的 CopyOnWriteArrayList 存储所有活跃的 WebSocket 会话
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public ChatWebSocketHandler(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 当新的 WebSocket 连接建立时调用
     *
     * @param session 新建立的 WebSocket 会话
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session); // 将新连接添加到会话列表
    }

    /**
     * 处理接收到的文本消息
     *
     * @param session 发送消息的 WebSocket 会话
     * @param message 收到的文本消息（JSON 格式，包含 sender 和 message）
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取用户发送的消息内容
        String userMessage = message.getPayload();
        // 调用 OpenAI 生成回复
        String aiResponse = this.chatModel.call(userMessage);
        // 将 AI 的回复发送回客户端
        TextMessage textMessage = new TextMessage(aiResponse);
        session.sendMessage(textMessage);
    }



    /**
     * 当 WebSocket 连接关闭时调用
     *
     * @param session 关闭的 WebSocket 会话
     * @param status  关闭状态
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session); // 从会话列表中移除关闭的连接
    }
}