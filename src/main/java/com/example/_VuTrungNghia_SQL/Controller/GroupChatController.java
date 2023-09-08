package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import com.example._VuTrungNghia_SQL.repository.MemberRepository;
import com.example._VuTrungNghia_SQL.services.GroupSevice;
import com.example._VuTrungNghia_SQL.services.MemberSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/list")
    public String listGroupChats(Model model) {
        List<GroupChat> groupChats = groupChatRepository.findAll();
        model.addAttribute("groupChats", groupChats);
        return "groupchat/list";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model)
    {
        List<GroupChat> groupChats = groupSevice.searchGroup(keyword);
        model.addAttribute("groupChats", groupChats);
        model.addAttribute("keyword", keyword);
        return "groupchat/list";
    }

    @GetMapping("/chat/{groupId}")
    public String chat(@PathVariable Long groupId, Model model) {
        // Lấy thông tin thành viên và trạng thái từ cơ sở dữ liệu (hoặc bất kỳ nguồn dữ liệu nào khác)
        List<GroupMember> members = memberSevice.getAllMember();

        model.addAttribute("groupMembers", members);

        return "chat/chat"; // Trả về tên trang web chat (chat.html)
    }

    @GetMapping("/create")
    public String addGroup(Model model){
        model.addAttribute("groupChats",new GroupChat());
        model.addAttribute("member",memberSevice.getAllMember());
        return "groupchat/create";
    }

    @PostMapping("/create")
    public String add(@Valid @ModelAttribute("groupChats") GroupChat groupChat, BindingResult result, Model model ) {
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

    @GetMapping("/join/{id}")
    public String joinChat(@PathVariable Long id){

        return "chat/chat";
    }



}