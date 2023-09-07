package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.GroupChat;
import com.example._VuTrungNghia_SQL.entity.User;
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
import java.util.List;

@Controller
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

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

        // Tạo đối tượng tin nhắn và đặt người gửi và người nhận
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(messageContent);
        chatMessage.setSender(loggedInUser);
        chatMessage.setReceiver(receiverUser); // Đảm bảo bạn đã đặt giá trị cho receiver

        // Lưu tin nhắn vào cơ sở dữ liệu
        messageService.saveMessage(chatMessage);

        return "redirect:/api/messages";
    }
    @GetMapping("/messages")
    public String getAllMessages(Model model) {
        List<ChatMessage> messages = messageService.getAllMessages();
        model.addAttribute("messages", messages);
        return "chat/messages"; // Điều hướng đến trang hiển thị tin nhắn
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