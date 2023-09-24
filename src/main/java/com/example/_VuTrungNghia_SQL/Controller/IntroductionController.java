package com.example._VuTrungNghia_SQL.Controller;


import com.example._VuTrungNghia_SQL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroductionController {

    @Autowired
    private UserService userService;
    @GetMapping("/introduction")
    public String introduction(Model model){
        int onlineUsersCount = (int) userService.countOnlineUsers();
        model.addAttribute("onlineUsersCount", onlineUsersCount);
        return "introduction/introduction";
    }
}
