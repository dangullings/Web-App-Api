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
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.LineItem;
import com.example.polls.service.LineItemService;

@RestController
@RequestMapping("/api/lineitems")
public class LineItemController {
    
    @Autowired
	private LineItemService service;

    @GetMapping
	public ResponseEntity<Page<LineItem>> findAll(Pageable pageable) {
		System.out.println("findAll controller"+pageable.getPageNumber()+" "+pageable.getPageSize());
		return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
	}

    @GetMapping("{id}")
	public ResponseEntity<LineItem> findById(@PathVariable Long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@PostMapping("/saveLineItem")
	public ResponseEntity<LineItem> save(@RequestBody LineItem order) {
		return new ResponseEntity<>(service.saveOrUpdate(order), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<LineItem> update(LineItem order) {
		return new ResponseEntity<>(service.saveOrUpdate(order), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/order/{orderId}")
	public void deleteByOrderId(@PathVariable Long orderId) {
		service.deleteByOrderId(orderId);
	}
}