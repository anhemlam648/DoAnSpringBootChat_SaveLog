package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import com.example._VuTrungNghia_SQL.services.GroupSevice;
import com.example._VuTrungNghia_SQL.services.MemberSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/groupchat")
public class GroupChatController {
    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private MemberSevice memberSevice;

    @Autowired
    private IuserRepository userRepository;
    @Autowired
    private GroupSevice groupSevice;

    @GetMapping("/list")
    public String listGroupChats(Model model) {
        List<GroupChat> groupChats = groupChatRepository.findAll();
        model.addAttribute("groupChats", groupChats);
        return "groupchat/list";
    }

    @GetMapping("/create")
    public String addGroup(Model model){
        model.addAttribute("groupChats",new GroupChat());
        model.addAttribute("member",memberSevice.getAllMember());
        return "groupchat/create";
    }

    @PostMapping("/create")
    public String addBook(@Valid @ModelAttribute("groupChats") GroupChat groupChat, BindingResult result, Model model ) {
        if(result.hasErrors())
        {
            model.addAttribute("member", memberSevice.getAllMember());
            return "groupchat/create";
        }
        groupSevice.save(groupChat);
        return "redirect:/groupchat/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteGroupChat(@PathVariable Long id) {
        groupChatRepository.deleteById(id);
        return "redirect:/groupchat/list";
    }

    //    @PostMapping("/join/{id}")
//    public String joinChat(@PathVariable Long id, Model model) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentUsername = userDetails.getUsername();
//
//        Optional<GroupChat> groupChatOptional = groupChatRepository.findById(id);
//
//        if (groupChatOptional.isPresent()) {
//            GroupChat groupChat = groupChatOptional.get();
//
//            boolean isAlreadyMember = groupChat.getGroupMembers().stream()
//                    .anyMatch(member -> member.getUser().getUsername().equals(currentUsername));
//
//            if (!isAlreadyMember) {
//                // Tham gia nhóm chat
//                GroupMember newMember = new GroupMember();
//                User currentUser = userRepository.findByUsername(currentUsername); // Lấy thông tin người dùng từ UserRepository
//                newMember.setUser(currentUser);
//                newMember.setGroupChat(groupChat);
//                groupChat.getGroupMembers().add(newMember);
//
//                // Cập nhật số lượng thành viên
//                groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() + 1);
//
//                // Lưu lại nhóm chat đã cập nhật
//                groupChatRepository.save(groupChat);
//            } else {
//                // Rời khỏi nhóm chat
//                groupChat.getGroupMembers().removeIf(member -> member.getUser().getUsername().equals(currentUsername));
//
//                // Cập nhật số lượng thành viên
//                groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() - 1);
//
//                // Lưu lại nhóm chat đã cập nhật
//                groupChatRepository.save(groupChat);
//            }
//        }
//
//        return "redirect:/groupchat/list";
//    }
//    @GetMapping("/join/{id}")
//    public String joinChat(@PathVariable Long id, Model model) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentUsername = userDetails.getUsername();
//
//        Optional<GroupChat> groupChatOptional = groupChatRepository.findById(id);
//
//        if (groupChatOptional.isPresent()) {
//            GroupChat groupChat = groupChatOptional.get(); // Khai báo và gán giá trị cho biến groupChat
//
//            boolean isAlreadyMember = groupChat.getGroupMembers().stream()
//                    .anyMatch(member -> member.getUser().getUsername().equals(currentUsername));
//
//            if (!isAlreadyMember) {
//                // Tham gia nhóm chat
//                GroupMember newMember = new GroupMember();
//                User currentUser = userRepository.findByUsername(currentUsername); // Lấy thông tin người dùng từ UserRepository
//                newMember.setUser(currentUser);
//                newMember.setGroupChat(groupChat);
//                groupChat.getGroupMembers().add(newMember);
//
//                // Cập nhật số lượng thành viên
//                groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() - 1);
//
//                // Lưu lại nhóm chat đã cập nhật
//                groupChatRepository.save(groupChat);
//            } else {
//                // Rời khỏi nhóm chat
//                groupChat.getGroupMembers().removeIf(member -> member.getUser().getUsername().equals(currentUsername));
//
//                // Cập nhật số lượng thành viên
//                groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() + 1);
//                // Lưu lại nhóm chat đã cập nhật
//                groupChatRepository.save(groupChat);
//            }
//
//            // Truyền số lượng thành viên vào trang "chat/chat"
//            model.addAttribute("numberOfMembers", groupChat.getNumberOfMembers());
//
//            // Chuyển hướng người dùng đến trang "chat/chat"
//            return "chat/chat";
//        } else {
//            // Xử lý lỗi nếu không tìm thấy nhóm chat
//            return "redirect:/groupchat/list";
//        }
//    }
    @GetMapping("/join/{id}")
    public String joinChat(@PathVariable Long id, Model model){
        return "chat/chat";
    }


}