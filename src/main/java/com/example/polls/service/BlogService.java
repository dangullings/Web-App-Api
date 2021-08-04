package com.example.polls.service;

import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.polls.model.Blog;
import com.example.polls.repository.BlogRepo;

@Service
public class BlogService {

	@Autowired
    private BlogRepo blogRepo;
	
	public Blog findById(Long id) {
		return blogRepo.findById(id).get();
	}

	public void saveOrUpdate(Blog object) {
		Optional<Blog> blog = blogRepo.findById(object.getId());
	    try {
			System.out.println(blog.get().getJsonData().getString("KEY"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    //prints out value for key
	    
	    JSONObject toSet = new JSONObject();
	    try {
			toSet.put("", blog.get());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    blog.get().setJsonData(toSet);
	    blogRepo.save(blog.get());
	}
	
}
