package com.example.polls.service;

import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Student;
import com.example.polls.model.User;
import com.example.polls.repository.StudentRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.User_Student_Repo;

@Service
public class UserService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private User_Student_Repo user_student_repo;
    @Autowired
    private UserRepository userRepo;
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Page<User> findAll(Pageable pageable) {
		return userRepo.findAll(pageable);
	}
    
	public List<Student> findAllStudentsByUserId(long userId) {
		List<Long> studentIds = user_student_repo.findAllStudentIdsByUserId(userId);
		List<Student> students = (List<Student>) studentRepository.findAllById(studentIds);
		
		return students;
	}
	
	public User saveOrUpdate(User user) {
		return userRepo.save(user);
	}
	
	public User findById(Long id) {
		return userRepo.findById(id).get();
	}
	
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			userRepo.deleteById(id);
			jsonObject.put("message", "User deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	public void updatePasswordResetToken(String passwordResetToken, String email) {
		
		String goodEmail = email;
		
		if (goodEmail.equalsIgnoreCase("dangullings@gmail.com")) {
			System.out.print("we equal each other! "+" "+goodEmail);
		} else {
			System.out.print("we do not equal each other! "+" "+goodEmail);
		}
		
		
		Optional<User> user = userRepo.findUserByEmail("dangullings@gmail.com");
		System.out.print("user service updatepwresettoken "+user+" "+email);
		return;
		
		
		//if (user != null) {
		//	user.setPasswordResetToken(passwordResetToken);
		//	System.out.print("user service user is not null");
		//	userRepo.save(user);
		//} else {
		//	System.out.print("user service user IS NULL");
		//	throw new ResourceNotFoundException("User not found with email "+email+".", email, user);
		//}
	}
	
	public Optional<User> findByPasswordResetToken(String passwordResetToken) {
		return userRepo.findByPasswordResetToken(passwordResetToken);
	}
	
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(newPassword);
		
		user.setPassword(encodePassword);
		user.setPasswordResetToken(null);
		
		userRepo.save(user);
	}
}