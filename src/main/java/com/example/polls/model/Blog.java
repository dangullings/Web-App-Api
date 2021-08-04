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
@Table(name = "blog")
public class Blog {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NonNull
    @Column(columnDefinition = "TEXT")
    @Convert(converter= JSONObjectConverter.class)
    private JSONObject jsonData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
	
}
