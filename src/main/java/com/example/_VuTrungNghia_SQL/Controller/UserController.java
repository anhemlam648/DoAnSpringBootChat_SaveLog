package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.services.EmailService;
import com.example._VuTrungNghia_SQL.services.UserService;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private EmailService emailService;
    @GetMapping("/login")
    public String login()
    {
        return "user/login";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("user",new User());
        return "user/register";
    }
//    @PostMapping("/register")
//    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,Model model)
//    {
//        if (bindingResult.hasErrors())
//        {
//            List<FieldError> errors = bindingResult.getFieldErrors();
//            for(FieldError error : errors)
//            {
//                model.addAttribute(error.getField() + "_error",error.getDefaultMessage());
//            }
//            return "user/register";
//        }
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        userService.save(user);
//        return "redirect:/login";
//    }
//    Email
@PostMapping("/register")
public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
        }
        return "user/register";
    }

    // Tạo và gửi OTP cho email
    String otpCode = generateOTP(6); // Hãy triển khai phương thức generateOTP() của riêng bạn.
    user.setEmailVerificationCode(otpCode);

    // Gửi OTP qua email
    boolean emailSent = emailService.sendEmail(user.getEmail(), "Xác thực OTP", "Mã OTP của bạn là: " + otpCode);
    if (!emailSent) {
        // Xử lý lỗi khi gửi email thất bại
        model.addAttribute("email_error", "Lỗi khi gửi email xác thực.");
        return "user/register";
    }
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    userService.save(user);
    return "user/otp-verification"; // Chuyển hướng đến trang xác thực OTP
}
    private String generateOTP(int length) {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }

        return otp.toString();
    }

}