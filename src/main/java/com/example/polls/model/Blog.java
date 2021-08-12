package com.example.polls.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.lang.NonNull;

import com.example.polls.util.JSONObjectConverter;

@Entity
@Table(name = "blogs")
public class Blog {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(columnDefinition = "TEXT")
    private String jsonData;
    
    private boolean active;
    
    private String date;
    
    private String author;
  

	public Blog() {
		super();
	}

	public Blog(Long id, String jsonData, boolean active, String date, String author) {
		super();
		this.id = id;
		this.jsonData = jsonData;
		this.active = active;
		this.date = date;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", jsonData=" + jsonData + "]";
	}
	
	
}
