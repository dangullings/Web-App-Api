package com.example.polls.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.ConfirmationToken;
import com.example.polls.model.Student;
import com.example.polls.model.User;
import com.example.polls.payload.UserIdentityAvailability;
import com.example.polls.payload.UserSummary;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmailSenderService;
import com.example.polls.service.UserService;
import com.example.polls.util.Utility;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailSenderService emailSenderService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping("/users")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
	}
    
    @GetMapping("/user/me")
    //@PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    	System.out.println("getCurrentUser "+currentUser.toString());
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return user;
    }
    
    @PostMapping("/saveUser")
	public ResponseEntity<User> save(@RequestBody User user) {
    	Boolean emailAvailable, usernameAvailable;
    	User currentUser = userService.findById(user.getId());
    	
    	if (currentUser == null) {
    		return new ResponseEntity<>(userService.saveOrUpdate(user), HttpStatus.CREATED);
    	}
    	
    	if (user.getEmail().equalsIgnoreCase(currentUser.getEmail())) {
    		emailAvailable = true;
    	} else {
    		emailAvailable = userRepository.existsByEmail(user.getEmail());
    	}
    	if (user.getUsername().equalsIgnoreCase(currentUser.getUsername())) {
    		usernameAvailable = true;
    	} else {
    		usernameAvailable = userRepository.existsByUsername(user.getEmail());
    	}
    	
    	if (emailAvailable && usernameAvailable) {
    		return new ResponseEntity<>(userService.saveOrUpdate(user), HttpStatus.CREATED);
    	} else {
    		if (!emailAvailable) {
    			user.setEmail("exists");
    		}
    		if (!usernameAvailable) {
    			user.setUsername("exists");
    		}
    		return new ResponseEntity<>(user, HttpStatus.CREATED);
    	}
	}
    
    @DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
	}
    
    @GetMapping("/users/{userId}/group")
    public List<Student> findAllStudentsByUserId(@PathVariable(value = "userId") long userId) {
        return userService.findAllStudentsByUserId(userId);
    }
    
    @GetMapping("/user/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
	}
    
	/*
	 * private void sendEmail(String email, String link) { SimpleMailMessage
	 * mailMessage = new SimpleMailMessage(); mailMessage.setTo(email);
	 * mailMessage.setSubject("Link to reset your password.");
	 * mailMessage.setFrom("dangullings.app@gmail.com");
	 * mailMessage.setText("You requested to reset your password. Reset it here: "
	 * +link);
	 * 
	 * emailSenderService.sendEmail(mailMessage); }
	 * 
	 * @PostMapping("/user/forgot_password") public String
	 * forgotPassword(HttpServletRequest request, @RequestBody String email) {
	 * String passwordResetToken = RandomString.make(45);
	 * 
	 * try { userService.updatePasswordResetToken(passwordResetToken, email); String
	 * resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" +
	 * passwordResetToken; sendEmail(email, resetPasswordLink);
	 * 
	 * } catch (ResourceNotFoundException ex) { return "error"; }
	 * 
	 * return ""; }
	 * 
	 * @RequestMapping("/reset_password") public ResponseEntity<?>
	 * resetPassword(@RequestParam("token") String token){ User user =
	 * userService.findByPasswordResetToken(token);
	 * 
	 * if (user != null) { System.out.println("user token found "+user.toString());
	 * }
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Location",
	 * "http://localhost:3000/students"); return new
	 * ResponseEntity("You can now login. Please close out this window. Check your email for login credentials."
	 * , HttpStatus.OK); }
	 */

}