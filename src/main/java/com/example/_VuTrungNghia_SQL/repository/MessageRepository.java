package com.example._VuTrungNghia_SQL.repository;


import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.User;
import jakarta.mail.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> { // Sửa kiểu dữ liệu thành ChatMessage và Long

    List<ChatMessage> findBySender(User sender);
    // Các phương thức truy vấn đặc custom có thể được thêm ở đây
    List<ChatMessage> findByGroupId(Long groupId);

    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage cm SET cm.seen = :seenStatus WHERE cm.id = :messageId")
    void updateSeenStatus(@Param("messageId") Long messageId, @Param("seenStatus") String seenStatus);
}