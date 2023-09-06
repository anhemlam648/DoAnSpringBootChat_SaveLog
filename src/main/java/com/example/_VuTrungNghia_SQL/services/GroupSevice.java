package com.example._VuTrungNghia_SQL.services;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupSevice {
    @Autowired
    private GroupChatRepository groupChatRepository;

    public void save(GroupChat groupChat) {
        groupChatRepository.save(groupChat);
    }
    public GroupChat findByGroupname(String groupName) {
        return groupChatRepository.findByGroupName(groupName);
    }
    public void deleteGroup(Long id){groupChatRepository.deleteById(id);
    }
    public GroupChat getGroupById(Long id){
        Optional<GroupChat> optional = groupChatRepository.findById(id);
        return optional.orElse(null);
    }
}