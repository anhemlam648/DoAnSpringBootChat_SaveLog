package com.example._VuTrungNghia_SQL.services;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
//    public Long createGroupChatAndGetGroupId() {
//        Optional<GroupChat> existingGroup = groupChatRepository.findById(groupChatRepository.findFirstGroupId());
//        if (existingGroup.isPresent()) {
//            // Nhóm chat đầu tiên đã tồn tại, bạn có thể lấy thông tin của nó từ existingGroup.get()
//            Long groupId = existingGroup.get().getId();
//            // Gọi các hoạt động khác mà bạn muốn thực hiện trên nhóm chat này
//            return groupId; // Trả về groupId ở đây
//        }
//        else {
//            // Không tìm thấy nhóm chat đầu tiên, bạn có thể tạo một nhóm chat mới và lấy thông tin của nó
//            GroupChat newGroup = new GroupChat();
//            groupChatRepository.save(newGroup);
//            Long groupId = newGroup.getId();
//            // Gọi các hoạt động khác mà bạn muốn thực hiện trên nhóm chat này
//            return groupId; // Trả về groupId ở đây
//        }
//    }

//    public List<Long> findAllGroupIds() {
//        // Use the repository to fetch all group IDs from your data source
//        return groupChatRepository.findAllGroupIds();
//    }
//    public Long createGroupChatAndGetGroupId() {
//        List<Long> existingGroupIds = findAllGroupIds();
//
//        if (!existingGroupIds.isEmpty()) {
//            // Check if there are at least three group IDs
//            if (existingGroupIds.size() >= 3) {
//                // Return the second-to-last group ID
//                Long groupIdToReturn = existingGroupIds.get(existingGroupIds.size() - 2);
//                return groupIdToReturn;
//            } else {
//                // If there are less than three group IDs, return the last one
//                Long groupIdToReturn = existingGroupIds.get(existingGroupIds.size() - 1);
//                return groupIdToReturn;
//            }
//        } else {
//            // No existing groups, so create a new one
//            GroupChat newGroup = new GroupChat();
//            groupChatRepository.save(newGroup);
//            return newGroup.getId();
//        }
//    }

}