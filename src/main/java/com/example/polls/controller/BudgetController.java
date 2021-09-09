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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.model.Budget;
import com.example.polls.service.BudgetService;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    
	@Autowired
	private BudgetService budgetService;
    
	@GetMapping("/expense/{expense}/begin/{beginDate}/end/{endDate}")
	public ResponseEntity<Page<Budget>> findAll(Pageable pageable, @PathVariable boolean expense, @PathVariable String beginDate, @PathVariable String endDate) {
		return new ResponseEntity<>(budgetService.findAll(pageable, expense, beginDate, endDate), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Budget> save(@RequestBody Budget budget) {
		System.out.println("findAll controller BUDGET save "+budget.isExpense());
		return new ResponseEntity<>(budgetService.saveOrUpdate(budget), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("{id}") 
	public void deleteAllByTypeAndAssignRef(@PathVariable String type, @PathVariable Long id) { 
		budgetService.deleteAllByTypeAndAssignRef(type, id); 
	}
	 
}