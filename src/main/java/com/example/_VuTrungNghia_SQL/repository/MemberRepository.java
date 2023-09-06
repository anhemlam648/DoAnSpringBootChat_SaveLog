package com.example._VuTrungNghia_SQL.repository;

import com.example._VuTrungNghia_SQL.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<GroupMember, Long> {
}