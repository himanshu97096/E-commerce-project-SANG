package com.sangspringproject.SANGSpringProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Send OTP to email
    public int sendOtp(String toEmail) {
        int otp = 100000 + new Random().nextInt(900000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("reset.sang@gmail.com"); // must match your Gmail account
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);

        mailSender.send(message);
        return otp;
    }
}