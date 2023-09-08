package com.example._VuTrungNghia_SQL.repository;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
    GroupChat findByGroupName(String groupName);

    @Query("SELECT MIN(id) FROM GroupChat")
    Long findFirstGroupId();

    Optional<GroupChat> findById(Long id);

    List<GroupChat> findAll();

    @Query("SELECT DISTINCT g.id FROM GroupChat g")
    List<Long> findAllGroupIds();

    @Query(value = "SELECT MAX(id) FROM GroupChat")
    Long findLatestGroupId();

    @Query("SELECT b FROM GroupChat b WHERE LOWER(b.groupName) LIKE %:keyword%")
            List<GroupChat> searchBooks(@Param("keyword") String keyword);


}