package com.example.polls.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.polls.exception.AppException;
import com.example.polls.model.ConfirmationToken;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.JwtAuthenticationResponse;
import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.SignUpRequest;
import com.example.polls.repository.ConfirmationTokenRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.EmailSenderService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	String passwordBeforeEncrypted;
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    
    @Autowired
    EmailSenderService emailSenderService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) { 
    	 Optional<User> user = Optional.ofNullable(new User());
    	 
    	 String userLabel = loginRequest.getUsernameOrEmail();
         Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
         Matcher mat = pattern.matcher(userLabel);

         if(mat.matches()){
        	 user = Optional.ofNullable(userRepository.findByEmail(userLabel));
         }else{
        	 user = userRepository.findByUsername(userLabel);
         }
         
         if (user.isPresent()) {
        	 if (!user.get().isEnabled()) {
        		 return new ResponseEntity(new ApiResponse(false, "user not enabled"),
                     HttpStatus.BAD_REQUEST);
        	 }
         }
    	 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        passwordBeforeEncrypted = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("user");
        
        User result = userRepository.save(user);
        
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        String url = "kkc-webapp-backend.herokuapp.com"; // localhost:8080
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("dangullings.app@gmail.com");
        mailMessage.setText("To complete your registration, click this link."
        +"http://"+url+"/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
	
    @RequestMapping("/confirm-account")
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken){
	  
	  ConfirmationToken token =confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	  
	  if (token != null) {
		  	Optional<User> optUser = userRepository.findByUsernameOrEmail(token.getUser().getUsername(), token.getUser().getEmail()); 
		  	User user = optUser.get();
		  	user.setEnabled(true); userRepository.save(user); 
		  	
		  	SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(user.getEmail());
	        mailMessage.setSubject("Registration Complete!");
	        mailMessage.setFrom("dangullings.app@gmail.com");
	        mailMessage.setText("Thank you for registering the app, "+user.getName()+"!\n\nUsername: "+user.getUsername()+"\n\nPassword: "+passwordBeforeEncrypted+"\n\nKeep this username and password for your records.");

	        emailSenderService.sendEmail(mailMessage);
	     
		}else{
		}
	  
	  	HttpHeaders headers = new HttpHeaders();
	    headers.add("Location", "https://kkc-webapp.herokuapp.com/login");    
	    return new ResponseEntity("Thank you, this confirms your email! You may close this window login at <a href=\"https://kkc-webapp.herokuapp.com/login\" target=\"_blank\">https://kkc-webapp.herokuapp.com/login</a>", HttpStatus.OK);	
    }
	 
     
}