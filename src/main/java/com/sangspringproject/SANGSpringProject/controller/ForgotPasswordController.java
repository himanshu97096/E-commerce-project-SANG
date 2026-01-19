package com.sangspringproject.SANGSpringProject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sangspringproject.SANGSpringProject.services.*;
import com.sangspringproject.SANGSpringProject.dao.*;
import com.sangspringproject.SANGSpringProject.models.User;


@Controller
public class ForgotPasswordController {
	
	private final userService userService = new userService();

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private userDao userDao;

    private int generatedOtp;
    private String userEmail;
    
    @PostMapping("/password_reset")
    public ModelAndView handlePasswordReset(@RequestParam("user_email") String email) {
        System.out.println("Submitted email: " + email);

        ModelAndView mView = new ModelAndView();

        boolean existsByEmail = userDao.userExistsByEmail(email);
        System.out.println("Email exists: " + existsByEmail);

        if (!existsByEmail) {
            mView.setViewName("password_reset"); // stay on form
            mView.addObject("error", "Email not registered!");
            return mView;
        }

        // Generate OTP and store email
        generatedOtp = emailService.sendOtp(email);
        userEmail = email;

        mView.setViewName("verify_otp"); // verify_otp.jsp
        mView.addObject("email", email);
        return mView;
    }

    
    @PostMapping("/verify_otp")
    public String verifyOtp(@RequestParam("otp") int otp,
                            @RequestParam("email") String email,
                            Model model) {
        if (otp == generatedOtp && email.equals(userEmail)) {
            model.addAttribute("email", email);
            return "update_password";
        } else {
            model.addAttribute("error", "Invalid OTP, try again!");
            model.addAttribute("email", email);
            return "verify_otp";
        }
    }

    @PostMapping("/update_password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword); // 🔒 hash if needed
            userDao.saveUser(user);
            model.addAttribute("message", "Password updated successfully! Please login.");
            return "userLogin";
        } else {
            model.addAttribute("error", "Something went wrong!");
            return "reset_password";
        }
    }
    
}
