package com.example._VuTrungNghia_SQL.Controller;


import com.example._VuTrungNghia_SQL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService; // Đảm bảo bạn đã inject UserService

    @GetMapping
    public String home(Model model){
        int onlineUsersCount = (int) userService.countOnlineUsers();
        model.addAttribute("onlineUsersCount", onlineUsersCount);
        return "home/index";
    }




}
