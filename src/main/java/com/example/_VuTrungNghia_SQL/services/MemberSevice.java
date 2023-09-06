package com.example._VuTrungNghia_SQL.services;

import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberSevice {
    @Autowired
    private MemberRepository memberRepository;

    public void save(GroupMember groupMember) {
        memberRepository.save(groupMember);
    }
    public void deleteGroup(Long id){memberRepository.deleteById(id);
    }
    public GroupMember getGroupMemberById(Long id){
        Optional<GroupMember> optional = memberRepository.findById(id);
        return optional.orElse(null);
    }
    public List<GroupMember> getAllMember(){
        return memberRepository.findAll();
    }
}
