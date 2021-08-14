package com.example.polls.service;

import java.time.LocalDateTime;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.polls.model.Blog;
import com.example.polls.model.Student;
import com.example.polls.repository.BlogRepo;

@Service
public class BlogService {

	@Autowired
    private BlogRepo blogRepo;
	
	public Blog saveOrUpdate(Long id, String object, boolean active, String date, String author, Long imageId) {
		//Optional<Blog> blog = blogRepo.findById(object.getId());
	    //try {
		//	System.out.println(object.get().getJsonData().getString("KEY"));
		//} catch (JSONException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
	    //prints out value for key
		//String stringToBeInserted = object.toString();
		
		//LocalDateTime now = LocalDateTime.now();  
		
		//String date = now.toString();
		
		System.out.println("blog service save "+date);
		
		Blog blog = new Blog(id, object, active, date, author, imageId);
		
		//Blog blog = new Blog();
		//blog.setJsonData(object);
		//blog.setId((long) 1);
	    //JSONObject toSet = new JSONObject();
	    //try {
		//	toSet.put("", object);
		//} catch (JSONException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	    //object.setJsonData(toSet);
	    return blogRepo.save(blog);
	}
	
	public Blog findById(Long id) {
		return blogRepo.findById(id).get(); 
	}
	
	public Page<Blog> findAllByActive(Pageable pageable, boolean active) {
		System.out.println("blog service findallbyactive "+active);
		return blogRepo.findAllByActive(pageable, active);
	}
	
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			blogRepo.deleteById(id);
			jsonObject.put("message", "deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}
