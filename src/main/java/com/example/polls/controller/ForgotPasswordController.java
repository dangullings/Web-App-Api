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
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;
     
    @Autowired
    private UserService userService;
     
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }
 
    @PostMapping("/forgot_password")
    public void processForgotPassword(HttpServletRequest request, @RequestBody String email, Model model) {
        String token = RandomString.make(30);
        
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/api/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
             
        } catch (ResourceNotFoundException ex) {
        } catch (UnsupportedEncodingException | MessagingException e) {
        }
           
    }
     
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
         
        helper.setFrom("dangullings.app@gmail.com", "Personal Project");
        helper.setTo(recipientEmail);
         
        String subject = "Here's the link to reset your password";
         
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
         
        helper.setSubject(subject);
         
        helper.setText(content, true);
         
        mailSender.send(message);
    }
     
     
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
    	
    	System.out.println("reset password show form "+token);
    	
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
         
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "";
        }
         
        return "reset_password_form";
    }
     
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        
        User user = userService.getByResetPasswordToken(token);
        
        model.addAttribute("title", "Reset your password");
         
        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "";
        } else {           
        	userService.updatePassword(user, password);
             
            model.addAttribute("message", "You have successfully changed your password.");
        }
         
        try {
			sendEmailPasswordChanged(user.getEmail(), password);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "message";
    }
    
    public void sendEmailPasswordChanged(String recipientEmail, String password)
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
    
}