package com.example._VuTrungNghia_SQL.repository;


import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.User;
import jakarta.mail.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> { // Sửa kiểu dữ liệu thành ChatMessage và Long

    List<ChatMessage> findBySender(User sender);
    // Các phương thức truy vấn đặc custom có thể được thêm ở đây
    List<ChatMessage> findByGroupId(Long groupId);
}