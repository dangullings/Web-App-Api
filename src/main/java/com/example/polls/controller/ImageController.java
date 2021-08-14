package com.example.polls.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Image;
import com.example.polls.model.Item;
import com.example.polls.service.ImageService;
import com.example.polls.service.ItemService;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    
    @Autowired
	private ImageService imageService;
    
    @GetMapping("/image/{id}")
	public ResponseEntity<Image> findById(@PathVariable Long id) {
    	System.out.println("get image"+id);
		return new ResponseEntity<>(imageService.findById(id), HttpStatus.OK);
	}
    
    @PostMapping("/saveImage/{id}")
	public ResponseEntity<Image> save(@RequestParam("file") MultipartFile uploadFile, @PathVariable Long id) {
		return new ResponseEntity<>(imageService.saveOrUpdate(uploadFile, id), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/image/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		return new ResponseEntity<>(imageService.deleteById(id), HttpStatus.OK);
	}
}