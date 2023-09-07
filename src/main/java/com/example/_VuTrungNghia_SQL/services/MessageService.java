package com.example._VuTrungNghia_SQL.services;


import com.example._VuTrungNghia_SQL.entity.ChatMessage; // Đổi import này
import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<ChatMessage> getAllMessages() {
        return messageRepository.findAll();
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return messageRepository.save(message);
    }
    public List<ChatMessage> getAllChat(){
        return messageRepository.findAll();
    }

    // Thêm các phương thức xử lý tin nhắn tùy chỉnh nếu cần
}