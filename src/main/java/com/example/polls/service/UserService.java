package com.example.polls.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Student> findAllStudentsByUserId(long userId) {
		List<Long> studentIds = user_student_repo.findAllStudentIdsByUserId(userId);
		List<Student> students = (List<Student>) studentRepository.findAllById(studentIds);
		
		System.out.println("user service user id "+userId);
		
		return students;
	}
	
	public User findById(Long id) {
		return userRepo.findById(id).get();
	}
}