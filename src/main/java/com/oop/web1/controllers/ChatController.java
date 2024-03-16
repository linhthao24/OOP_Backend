package com.oop.web1.controllers;

import com.oop.web1.repositories.ChatRepository;
import com.oop.web1.dto.ChatDto;
import com.oop.web1.entitys.QAAEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
@RestController
@Configuration
@EnableWebSocket
public class ChatController implements WebSocketConfigurer{
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/chat").setAllowedOrigins("*");
    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ChatController.MyWebSocketHandler();
    }
    public class MyWebSocketHandler extends TextWebSocketHandler {
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            // Nhận tin nhắn từ người dùng
            String question = message.getPayload();
            // Phân tích tin nhắn và tìm đáp án trong cơ sở dữ liệu
            String answer = findAnswer(question);
            // Gửi đáp án về cho người dùng
            session.sendMessage(new TextMessage(answer));
        }

        private String findAnswer(String question) {
            // Tìm câu hỏi trong cơ sở dữ liệu và trả về đáp án tương ứng
            List<QAAEntity> qaas = chatRepository.findAll();
            for (QAAEntity qaa : qaas) {
                if (question.toLowerCase().contains(qaa.getQuestion().toLowerCase())) {
                    return qaa.getAnswer();
                }
            }
            return "Xin lỗi, không tìm thấy đáp án cho câu hỏi này.";
        }
    }
}
