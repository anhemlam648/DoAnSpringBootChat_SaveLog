package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private GroupChatRepository groupChatRepository;

    @GetMapping("show/{groupId}")
    public String showMembers( @PathVariable Long groupId, Model model) {
        if (groupId != null) {
            Optional<GroupChat> groupChatOptional = groupChatRepository.findById(groupId);

            if (groupChatOptional.isPresent()) {
                GroupChat groupChat = groupChatOptional.get();
                model.addAttribute("groupChat", groupChat);
                return "members/list"; // Tạo một view mới cho danh sách thành viên
            } else {
                // Xử lý lỗi nếu không tìm thấy nhóm chat
                return "redirect:/groupchat/list";
            }
        } else {
            // Xử lý trường hợp không có giá trị groupId
            return "redirect:/groupchat/list";
        }
    }
}
