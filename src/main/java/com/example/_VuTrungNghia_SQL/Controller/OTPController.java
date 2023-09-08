package com.example._VuTrungNghia_SQL.Controller;


import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example._VuTrungNghia_SQL.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OTPController {

    @Autowired
    private UserService userService;
//    @GetMapping("/otp-verification")
//    public String otpVerificationForm(Model model) {
//        return "user/otp-verification";
//    }
//    @PostMapping("/otp-verification")
//    public String OTP(@RequestParam String otpCode, @RequestParam String email, Model model) {
//        User user = userService.findByUsername(email);
//        if (otpCode.equals(user.getEmailVerificationCode())) {
//            //Xác thực thành công
//            user.setEmailVerified(true);
//            return "user/success-page"; // Chuyển hướng đến trang đăng nhập
//        } else {
//            // Xác thực thất bại
//            model.addAttribute("otp_error", "Mã OTP không hợp lệ.");
//            return "/404";
//        }
//
//    }
}