package com.example._VuTrungNghia_SQL.repository;


import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import jakarta.mail.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> { // Sửa kiểu dữ liệu thành ChatMessage và Long

    // Các phương thức truy vấn đặc custom có thể được thêm ở đây
}