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

import com.example.polls.model.Order;
import com.example.polls.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
	private OrderService orderService;

    @GetMapping
	public ResponseEntity<Page<Order>> findAll(Pageable pageable) {
		System.out.println("findAll controller"+pageable.getPageNumber()+" "+pageable.getPageSize());
		return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
	}

    @GetMapping("{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
	}

	@PostMapping("/saveOrder")
	public ResponseEntity<Order> save(@RequestBody Order order) {
		return new ResponseEntity<>(orderService.saveOrUpdate(order), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<Order> update(Order order) {
		return new ResponseEntity<>(orderService.saveOrUpdate(order), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		return new ResponseEntity<>(orderService.deleteById(id), HttpStatus.OK);
	}
}