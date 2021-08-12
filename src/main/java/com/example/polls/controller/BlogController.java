package com.example.polls.controller;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.Blog;
import com.example.polls.model.Student;
import com.example.polls.model.Test;
import com.example.polls.service.BlogService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@PostMapping("/save/id/{id}/active/{active}/date/{date}/author/{author}")
	public ResponseEntity<Blog> save(@RequestBody String object, @PathVariable Long id, @PathVariable boolean active, @PathVariable String date, @PathVariable String author) {
		System.out.println("blog controller save "+id+" "+object);
		return new ResponseEntity<>(blogService.saveOrUpdate(id, object, active, date, author), HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Blog> findById(@PathVariable Long id) {
		return new ResponseEntity<>(blogService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/active/{active}")
	public ResponseEntity<Page<Blog>> findAllByActive(Pageable pageable, @PathVariable boolean active) {
		System.out.println("blog controller findallbyactive "+active);
		return new ResponseEntity<>(blogService.findAllByActive(pageable, active), HttpStatus.OK);
	}

}
