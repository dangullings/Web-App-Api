package com.example.polls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.polls.model.Budget;
import com.example.polls.model.CalendarEvent;
import com.example.polls.repository.BudgetRepo;

@Service
public class BudgetService {

	@Autowired
    private BudgetRepo budgetRepo;
	
	public Page<Budget> findAll(Pageable pageable, boolean expense, String beginDate, String endDate) {
		return budgetRepo.findAll(pageable, expense, beginDate, endDate);
	}
	
	public Budget saveOrUpdate(Budget budget) {
		return budgetRepo.save(budget);
	}
	
	public void deleteAllByTypeAndAssignRef(String type, Long id) {
		budgetRepo.deleteAllByTypeAndAssignRef(type, id);
	}
	
}
