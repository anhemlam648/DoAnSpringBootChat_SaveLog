package com.example._VuTrungNghia_SQL.services;


import com.example._VuTrungNghia_SQL.entity.ChatMessage; // Đổi import này
import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.entity.User;
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
    public List<ChatMessage> getMessagesForUser(User user) {
        // Triển khai truy vấn cơ sở dữ liệu để lấy danh sách tin nhắn cho người dùng cụ thể
        // Ở đây, chúng ta giả sử có một cột 'sender' trong ChatMessage để lưu người gửi
        // Bạn cần điều chỉnh truy vấn tùy theo cơ sở dữ liệu của bạn
        return messageRepository.findBySender(user);
    }

    public List<ChatMessage> getMessagesByGroupId(Long groupId) {
        return messageRepository.findByGroupId(groupId);
    }
    public ChatMessage getMessageById(Long messageId) {
        // Thực hiện logic để tìm tin nhắn theo ID và trả về
        return messageRepository.findById(messageId).orElse(null);
    }
}