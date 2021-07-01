package com.example.polls.controller;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.User;
import com.example.polls.service.UserService;
import com.example.polls.util.Utility;

import net.bytebuddy.utility.RandomString;
 
@Controller
@RequestMapping("/api")
public class ChangePasswordController {
    @Autowired
    private JavaMailSender mailSender;
     
    @Autowired
    private UserService userService;
     
    public void sendEmail(String recipientEmail, String password)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        helper.setFrom("dangullings.app@gmail.com", "Personal Project");
        helper.setTo(recipientEmail);
         
        String subject = "Your password has been changed.";
         
        String content = "<p>Hello,</p>"
                + "<p>You have changed your password.</p>"
                + "<p>Password: " + password + "</p>"
                + "<p>If you didn't change your password, reset it now.</p>";
         
        helper.setSubject(subject);
         
        helper.setText(content, true);
         
        mailSender.send(message);
    }
     
    @PostMapping("/change_password")
    public void processChangePassword(@RequestBody User user) {
    	String newPassword = user.getPassword();
    	
        userService.updatePassword(user, newPassword);
        
        try {
			sendEmail(user.getEmail(), newPassword);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}