package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import com.example._VuTrungNghia_SQL.repository.MessageRepository;
import com.example._VuTrungNghia_SQL.services.GroupSevice;
import com.example._VuTrungNghia_SQL.services.MessageService;
import com.example._VuTrungNghia_SQL.services.UserService;
import jakarta.mail.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupChatRepository groupChatRepository;
    @Autowired
    private GroupSevice groupSevice;

    @Autowired
    private UserService userService;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    //    @GetMapping("/create")
//    public String add(Model model){
//        model.addAttribute("chatmessage",new ChatMessage());
//        model.addAttribute("users",userServicel.getAllUser());
//        return "chat/chat";
//    }
//
//    @PostMapping("/create")
//    public String addChat(@Valid @ModelAttribute("chatmessage") ChatMessage chatMessage, BindingResult result, Model model ) {
//        if(result.hasErrors())
//        {
//            model.addAttribute("users",userServicel.getAllUser());
//            return "groupchat/create";
//        }
//        messageService.saveMessage(chatMessage);
//        return "redirect:/chat/chat";
//    }
//    @GetMapping("/create")
//    public String add(Model model, Principal principal) {
//        String loggedInUsername = principal.getName();
//        User loggedInUser = userService.getUserByUsername(loggedInUsername);
//
//        model.addAttribute("chatmessage", new ChatMessage());
//        model.addAttribute("users", userService.getAllUser());
//        model.addAttribute("loggedInUser", loggedInUser); // Truyền thông tin người dùng đang đăng nhập
//        return "chat/chat";
//    }
    @GetMapping("/create")
    public String chat(Model model, Principal principal) {
        String loggedInUsername = principal.getName();
        User loggedInUser = userService.getUserByUsername(loggedInUsername);

        // Lấy danh sách tin nhắn từ cơ sở dữ liệu (ví dụ)
        List<ChatMessage> messages = messageService.getMessagesForUser(loggedInUser);

        model.addAttribute("messages", messages);
        model.addAttribute("loggedInUser", loggedInUser);
        return "chat/chat";
    }

    @PostMapping("/create")
    public String addChat(Model model, Principal principal, HttpServletRequest request) {
        String loggedInUsername = principal.getName();
        User loggedInUser = userService.getUserByUsername(loggedInUsername);

        String messageContent = request.getParameter("content"); // Lấy giá trị của trường tin nhắn

        // Lấy người nhận tin nhắn (ở đây là một ví dụ, bạn cần cung cấp giá trị thích hợp)
        User receiverUser = userService.getUserByUsername("DEMO4");
//        Long groupId = groupSevice.createGroupChatAndGetGroupId();

        // Tạo đối tượng tin nhắn và đặt người gửi và người nhận
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(messageContent);
        chatMessage.setSender(loggedInUser);
        chatMessage.setReceiver(receiverUser); // Đảm bảo bạn đã đặt giá trị cho receiver
        Long groupId = 1L;
        chatMessage.setGroupId(groupId);
        // Lưu tin nhắn ào cơ sở dữ liệu
        messageService.saveMessage(chatMessage);

        return "redirect:/api/messages/" + groupId;
    }
//    @GetMapping("/messages")
//    public String getAllMessages(Model model) {
//        List<ChatMessage> messages = messageService.getAllMessages();
//        model.addAttribute("messages", messages);
//        return "chat/messages"; // Điều hướng đến trang hiển thị tin nhắn
//    }
//@GetMapping("/messages/{groupId}")
//public String getMessagesForGroup(@PathVariable Long groupId, Model model) {
//    // Lấy danh sách tin nhắn cho nhóm chat cụ thể (sử dụng groupId để xác định nhóm chat)
//    List<ChatMessage> messages = messageService.getMessagesByGroupId(groupId);
//    model.addAttribute("messages", messages);
//    model.addAttribute("groupId", groupId);
//    return "chat/messages";
//}
@GetMapping("/messages/{groupId}")
public String getMessagesForGroup(@PathVariable Long groupId, Model model) {
    // Lấy danh sách tin nhắn cho nhóm chat cụ thể (sử dụng groupId để xác định nhóm chat)
    List<ChatMessage> messages = messageService.getMessagesByGroupId(groupId);

    // Lấy thông tin của nhóm chat, bao gồm số lượng thành viên
    GroupChat groupChat = groupChatRepository.findById(groupId).orElse(null);

    if (groupChat != null) {
        // Increment the member count
        groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() + 1);
        groupChatRepository.save(groupChat);
    }

    model.addAttribute("messages", messages);
    model.addAttribute("groupId", groupId);
    return "chat/messages";
}
    @GetMapping("/leave-group/{groupId}")
    public String leaveGroup(@PathVariable Long groupId, Principal principal) {
        // Lấy thông tin của nhóm chat
        GroupChat groupChat = groupChatRepository.findById(groupId).orElse(null);

        if (groupChat != null) {
            // Kiểm tra xem người dùng có phải là thành viên của nhóm không
            boolean isUserMember = isUserMember(groupChat, principal.getName());

            if (isUserMember) {
                // Người dùng là thành viên của nhóm, hãy xóa họ khỏi danh sách thành viên
                removeUserFromGroup(groupChat, principal.getName());
                groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() - 1);
                groupChatRepository.save(groupChat);
            }
        }

        // Chuyển hướng người dùng đến trang khác sau khi rời nhóm (ví dụ: trang chính)
        return "redirect:/groupchat/list"; // Thay đổi đường dẫn tùy theo trang bạn muốn chuyển hướng sau khi rời nhóm.
    }
    private boolean isUserMember(GroupChat groupChat, String username) {
        // Kiểm tra xem người dùng đã là thành viên của nhóm chưa
        List<GroupMember> groupMembers = groupChat.getGroupMembers();
        for (GroupMember member : groupMembers) {
            if (member.getUser().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    private void removeUserFromGroup(GroupChat groupChat, String username) {
        List<GroupMember> groupMembers = groupChat.getGroupMembers();
        Iterator<GroupMember> iterator = groupMembers.iterator();
        while (iterator.hasNext()) {
            GroupMember member = iterator.next();
            if (member.getUser().getUsername().equals(username)) {
                iterator.remove();
            }
        }
        groupChatRepository.save(groupChat);
    }

//    @MessageMapping("/send")
//    @SendTo("/topic/messages")
//    public ChatMessage sendMessage(ChatMessage chatMessage) {
//        // Xử lý và lưu tin nhắn vào cơ sở dữ liệu ở đây (đã thêm vào cơ sở dữ liệu)
//
//        // Gửi tin nhắn cho người nhận thông qua WebSocket
//        messagingTemplate.convertAndSendToUser(
//                chatMessage.getReceiver().getUsername(),
//                "/topic/messages",
//                chatMessage
//        );
//
//        return chatMessage;
//    }
}