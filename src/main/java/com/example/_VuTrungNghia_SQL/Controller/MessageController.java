package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.entity.GroupMember;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.GroupChatRepository;
import com.example._VuTrungNghia_SQL.repository.MemberRepository;
import com.example._VuTrungNghia_SQL.repository.MessageRepository;
import com.example._VuTrungNghia_SQL.services.GroupSevice;
import com.example._VuTrungNghia_SQL.services.MessageService;
import com.example._VuTrungNghia_SQL.services.UserService;
import jakarta.mail.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MemberRepository memberRepository;

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

//    @PostMapping("/create")
//    public String addChat(Model model, Principal principal, HttpServletRequest request) {
//        String loggedInUsername = principal.getName();
//        User loggedInUser = userService.getUserByUsername(loggedInUsername);
//
//        String messageContent = request.getParameter("content"); // Lấy giá trị của trường tin nhắn
//
//        // Lấy người nhận tin nhắn (ở đây là một ví dụ, bạn cần cung cấp giá trị thích hợp)
//        User receiverUser = userService.getUserByUsername("DEMO4");
////        Long groupId = groupSevice.createGroupChatAndGetGroupId();
//
//        // Tạo đối tượng tin nhắn và đặt người gửi và người nhận
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setContent(messageContent);
//        chatMessage.setSender(loggedInUser);
//        chatMessage.setReceiver(receiverUser); // Đảm bảo bạn đã đặt giá trị cho receiver
//        Long groupId = 1L;
//        chatMessage.setGroupId(groupId);
//        // Lưu tin nhắn ào cơ sở dữ liệu
//        messageService.saveMessage(chatMessage);
//
//        return "redirect:/api/messages/" + groupId;
//    }
//@PostMapping("/create")
//public String addChat(Model model, Principal principal, HttpServletRequest request) {
//    String loggedInUsername = principal.getName();
//    User loggedInUser = userService.getUserByUsername(loggedInUsername);
//
//    String messageContent = request.getParameter("content"); // Lấy giá trị của trường tin nhắn
//
//    // Lấy danh sách tất cả người dùng
//    List<User> allUsers = userService.getAllUser();
//
//    // Tạo đối tượng tin nhắn và đặt người gửi
//    ChatMessage chatMessage = new ChatMessage();
//    chatMessage.setContent(messageContent);
//    chatMessage.setSender(loggedInUser);
//    Long groupId = 1L;
//    chatMessage.setGroupId(groupId);
//
//    // Lặp qua danh sách người dùng và gửi tin nhắn cho mỗi người dùng
//    for (User receiverUser : allUsers) {
//        chatMessage.setReceiver(receiverUser);
//        // Lưu tin nhắn vào cơ sở dữ liệu
//        messageService.saveMessage(chatMessage);
//    }
//
//    return "redirect:/api/messages/" + groupId;
//}
@PostMapping("/create")
public String addChat(
        Model model,
        Principal principal,
        @RequestParam("content") String messageContent,
        @RequestParam("file") MultipartFile file
) {
    // Lấy thông tin người dùng đã đăng nhập
    String loggedInUsername = principal.getName();
    User loggedInUser = userService.getUserByUsername(loggedInUsername);

    // Xác định người dùng cần chặn
    // ...
    loggedInUser.setOnlineStatus("ONLINE");
    userService.save(loggedInUser);

    // Tạo đối tượng tin nhắn và đặt người gửi
    Long groupId = 1L; // Thay đổi groupId theo nhu cầu của bạn

    // Xử lý file đính kèm (nếu có)
    byte[] attachedFileData = null;
    String attachedFileName = null;

    if (!file.isEmpty()) {
        long fileSize = file.getSize(); // Lấy kích thước tệp (byte)

        // Kiểm tra kích thước tệp (ví dụ: giới hạn 1MB)
        if (fileSize > 1 * 1024 * 1024) {
            // Xử lý lỗi nếu tệp quá lớn
        } else {
            try {
                // Lưu dữ liệu của file vào tin nhắn
                attachedFileData = file.getBytes();
                attachedFileName = file.getOriginalFilename();
            } catch (IOException e) {
                // Xử lý lỗi nếu có
            }
        }
    }
    ChatMessage newChatMessage = new ChatMessage();
    // Kiểm tra xem người gửi có bị chặn không
    if (!"BLOCKED".equals(loggedInUser.getBlockStatus())) {
        // Lặp qua danh sách người dùng và gửi tin nhắn cho mỗi người dùng không bị chặn
        List<User> allUsers = userService.getAllUser();
        for (User receiverUser : allUsers) {
            // Kiểm tra xem người nhận có phải là người dùng bị chặn không
            if (!"BLOCKED".equals(receiverUser.getBlockStatus())) {
                // Tạo một tin nhắn mới cho mỗi người nhận
                newChatMessage.setContent(messageContent);
                newChatMessage.setSender(loggedInUser);
                newChatMessage.setGroupId(groupId);
                newChatMessage.setReceiver(receiverUser);
                if (receiverUser.equals(loggedInUser)) {
                    // Nếu người nhận là người gửi, đánh dấu là "đã gửi" và "chưa xem"
                    newChatMessage.setSent("đã gửi");
                    newChatMessage.setSeen("đã nhận");
                } else {
                    // Nếu không phải người gửi, đánh dấu là "đã gửi" và "chưa xem"
                    newChatMessage.setSent("đã gửi");
                    newChatMessage.setSeen("đã nhận");
                }
                // Đặt file đính kèm nếu có
                newChatMessage.setAttachedFile(attachedFileData);
                newChatMessage.setAttachedFileName(attachedFileName);

                // Lưu tin nhắn vào cơ sở dữ liệu (nếu bạn muốn)
                messageService.saveMessage(newChatMessage);
            }
        }
    }

    return "redirect:/api/messages/" + groupId;
}

    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id) {
        // Cập nhật trạng thái block của người dùng với ID = id thành true
        userService.blockUser(id);
        return "redirect:/show/users"; // Chuyển hướng lại danh sách người dùng
    }

    @GetMapping("/unblock/{id}")
    public String unblockUser(@PathVariable Long id) {
        // Cập nhật trạng thái block của người dùng với ID = id thành false
        userService.unblockUser(id);
        return "redirect:/show/users"; // Chuyển hướng lại danh sách người dùng
    }

    @PostMapping("/updateSeenStatus/{messageId}")
    public ResponseEntity<String> updateSeenStatus(@PathVariable Long messageId, @RequestBody Map<String, String> requestBody) {
        String seenStatus = requestBody.get("seen");

        messageService.markMessageAsSeen(messageId);

        return ResponseEntity.ok("Trạng thái đã xem đã được cập nhật.");
    }


//

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
            // Xóa người dùng khỏi danh sách thành viên
//            removeUserFromGroup(groupChat, principal.getName());
            groupChat.setNumberOfMembers(groupChat.getNumberOfMembers() - 1);
            groupChatRepository.save(groupChat);

            User loggedInUser = userService.getUserByUsername(principal.getName());
            loggedInUser.setOnlineStatus("OFFLINE");
            userService.save(loggedInUser);
        }

        // Chuyển hướng người dùng đến trang khác sau khi rời nhóm (ví dụ: trang chính)
        return "redirect:/groupchat/list"; // Thay đổi đường dẫn tùy theo trang bạn muốn chuyển hướng sau khi rời nhóm.
    }
    private boolean fixUserMember(GroupChat groupChat, String username) {
        // Kiểm tra xem người dùng có trong danh sách thành viên của nhóm chat không
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
//    @GetMapping("/intro")
//    public String show()
//    {
//        return "introduction/introduction";
//    }
//    @PostMapping("/login")
//    public String login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password) {
//        // Thực hiện đăng nhập logic ở đây
//
//        // Sau khi đăng nhập thành công, cập nhật trạng thái người dùng thành "ONLINE"
//        User user = userService.getUserByUsername(username);
//        if (user != null) {
//            user.setOnlineStatus("ONLINE");
//            userService.save(user); // Cập nhật thông tin người dùng vào cơ sở dữ liệu
//        }
//        return "home/index";
//    }
}