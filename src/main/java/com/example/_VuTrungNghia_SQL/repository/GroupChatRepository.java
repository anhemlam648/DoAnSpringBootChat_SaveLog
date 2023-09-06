package com.example._VuTrungNghia_SQL.repository;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
    GroupChat findByGroupName(String groupName);
}