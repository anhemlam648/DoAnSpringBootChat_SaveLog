package com.example._VuTrungNghia_SQL.Controller;

//import com.example._VuTrungNghia_SQL.entity.Status;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import com.example._VuTrungNghia_SQL.services.EmailService;

import com.example._VuTrungNghia_SQL.services.UserService;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Controller
public class UserController {
    private static final Logger logInfo = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private IuserRepository userRepository;
    private String storedOTP;
    @Autowired
    private EmailService emailService;
    @GetMapping("/login")
    public String login(Model model)
    {
        int onlineUsersCount = (int) userService.countOnlineUsers();
        model.addAttribute("onlineUsersCount", onlineUsersCount);
        logInfo.info("Người dùng vừa vào trang đăng nhập");
        return "user/login";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("user",new User());
        return "user/register";
    }


//
//        // Thực hiện đăng xuất logic ở đây
//
//        return "redirect:/";
//    }

    //
//        // Your logout logic here
//
//        return "redirect:/login";
//    }
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
    logInfo.info("Người dùng vừa đăng ký");
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    userService.save(user);
    return "redirect:/otp-verification"; // Chuyển hướng đến trang xác thực OTP
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
    @GetMapping("/success-page")
    public String otpVerificationForm(Model model) {
        return "home/index";
    }

    @GetMapping("/otp-verification")
    public String Form(Model model) {
        return "user/otp-verification";
    }

//    @PostMapping("/otp-verification")
//    public String verifyOTP(@RequestParam("otpCode") String otpCode, Model model) {
//        // Lấy thông tin người dùng từ cơ sở dữ liệu bằng email hoặc ID của người dùng
//        String emailToSearch = "anhemlam123456@gmail.com";
//        User user = iuserRepository.findByEmail(emailToSearch);
//
//        // Kiểm tra xem người dùng tồn tại và mã OTP có tồn tại
//        if (user != null && user.getEmailVerificationCode() != null) {
//            // Kiểm tra xem mã OTP khớp với mã đã lưu
//            if (user.getEmailVerificationCode().equals(otpCode)) {
//                // Mã OTP hợp lệ và khớp, đánh dấu là đã sử dụng và lưu vào cơ sở dữ liệu
//                user.setEmailVerificationCode(null); // Đánh dấu là đã sử dụng
//                userService.save(user);
//
//                // Mã OTP hợp lệ, thực hiện đăng nhập hoặc xử lý khác ở đây
//                return "redirect:/success-page"; // Hoặc chuyển hướng đến trang bạn muốn sau khi đăng nhập thành công
//            }
//        }
//
//        // Mã OTP không hợp lệ hoặc không khớp, hiển thị thông báo lỗi
//        model.addAttribute("otp_error", "Mã OTP không hợp lệ hoặc không khớp. Vui lòng thử lại.");
//        return "user/otp-verification";
//    }

    @PostMapping("/otp-verification")
    public String verifyOTP(@RequestParam("otpCode") String otpCode, Model model) {
        // Lấy danh sách người dùng từ cơ sở dữ liệu bằng email
        String emailToSearch = "vunghia467@gmail.com";
        List<User> users = userRepository.findByEmail(emailToSearch);

        // Kiểm tra xem có người dùng nào khớp với mã OTP
        for (User user : users) {
            if (user != null && user.getEmailVerificationCode() != null && user.getEmailVerificationCode().equals(otpCode)) {
                // Mã OTP hợp lệ và khớp, đánh dấu là đã sử dụng và lưu vào cơ sở dữ liệu
                user.setEmailVerificationCode(null); // Đánh dấu là đã sử dụng
                userService.save(user);

                // Mã OTP hợp lệ, thực hiện đăng nhập hoặc xử lý khác ở đây
                return "redirect:/success-page"; // Hoặc chuyển hướng đến trang bạn muốn sau khi đăng nhập thành công
            }
        }

        // Mã OTP không hợp lệ hoặc không khớp, hiển thị thông báo lỗi
        model.addAttribute("otp_error", "Mã OTP không hợp lệ hoặc không khớp. Vui lòng thử lại.");
        return "user/otp-verification";
    }
//    @GetMapping("/logout")
//    public String processLogout(@SessionAttribute("loggedInUser") User loggedInUser) {
//        if (loggedInUser != null) {
//            // Cập nhật trạng thái người dùng thành "OFFLINE" khi đăng xuất
//            loggedInUser.getStatus();
//            loggedInUser.setStatus("offline");
//            userService.save(loggedInUser); // Lưu trạng thái vào cơ sở dữ liệu
//        }
//
//        // Xóa thông tin người dùng khỏi session và đăng xuất
//        return "redirect:/login";
//    }

}