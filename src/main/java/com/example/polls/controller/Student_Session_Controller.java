package com.example.polls.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.CalendarEvent_Student;
import com.example.polls.model.ClassSession;
import com.example.polls.model.Student;
import com.example.polls.model.Student_Session;
import com.example.polls.repository.ClassSessionRepo;
import com.example.polls.repository.StudentRepository;
import com.example.polls.service.Student_Session_Service;

@RestController
@RequestMapping("/api/studentsessions")
public class Student_Session_Controller {

	@Autowired
	private Student_Session_Service student_session_service;
	@Autowired
	private ClassSessionRepo session_repo;
	@Autowired
	private StudentRepository student_repo;
	
	@GetMapping("/session/{sessionId}")
	public List<Student_Session> findAllSessionsBySessionId(@PathVariable Long sessionId) {
		return student_session_service.findAllSessionsBySessionId(sessionId);
	}
	
	@GetMapping("/{studentId}/sessions")
    public List<ClassSession> findAllByStudentId(@PathVariable(value = "studentId") long studentId) {
        List<Student_Session> student_session_list =  student_session_service.findAllByStudentId(studentId);
        List<Long> sessionList = new ArrayList<Long>();
        for (Student_Session ss : student_session_list) {
            sessionList.add(ss.getClassSessionId());
        }
        
        if (sessionList != null) {
        	return session_repo.findAllById(sessionList);
        }
        
		return null;
    }
	
	@GetMapping("/{sessionId}/students")
    public List<Student> findAllBySessionId(@PathVariable(value = "sessionId") long sessionId) {
        List<Long> studentIds =  student_session_service.findAllBySessionId(sessionId);
        
        return (List<Student>) student_repo.findAllById(studentIds);
    }
	
	@PostMapping("/saveStudentSession")
	public ResponseEntity<Student_Session> save(@RequestBody Student_Session studentSession) {
		return new ResponseEntity<>(student_session_service.saveOrUpdate(studentSession), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/student/{studentId}")
	public void deleteAllByStudentId(@PathVariable Long studentId) {
		student_session_service.deleteAllByStudentId(studentId);
	}
	
	@DeleteMapping("/session/{sessionId}")
	public void deleteAllBySessionId(@PathVariable Long sessionId) {
		student_session_service.deleteAllBySessionId(sessionId);
	}
	
	@DeleteMapping("/session/{sessionId}/student/{studentId}")
	public void deleteAllByStudentId(@PathVariable Long sessionId, @PathVariable Long studentId) {
		student_session_service.deleteAllBySessionIdAndStudentId(sessionId, studentId);
	}
	
}
