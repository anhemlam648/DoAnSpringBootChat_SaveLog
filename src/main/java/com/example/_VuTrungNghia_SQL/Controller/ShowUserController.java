package com.example._VuTrungNghia_SQL.Controller;


import com.example._VuTrungNghia_SQL.entity.Book;
import com.example._VuTrungNghia_SQL.entity.Category;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import com.example._VuTrungNghia_SQL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/show")
public class ShowUserController {

    @Autowired
    private IuserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        Optional<User> editUserOptional = userRepository.findById(id);
        if (!editUserOptional.isPresent()) {
            return "redirect:/show/users";
        }
        User user = editUserOptional.get();
        model.addAttribute("user", user); // Sửa đổi tên thuộc tính từ "users" thành "user"
        return "user/edit"; // Sửa đổi đường dẫn trả về từ "book/edit" thành "user/edit"
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        Optional<User> editUserOptional = userRepository.findById(id);
        if (!editUserOptional.isPresent()) {
            return "redirect:/show/users";
        }
        User editUser = editUserOptional.get();
        editUser.setName(user.getName());
        editUser.setEmail(user.getEmail());
        editUser.setGioitinh(user.getGioitinh());
        editUser.setNgaysinh(user.getNgaysinh());
        userRepository.save(editUser);
        return "redirect:/show/users";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        return "redirect:/show/users";
    }
}
