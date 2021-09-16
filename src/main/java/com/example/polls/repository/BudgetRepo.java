package com.example.polls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.Budget;
import com.example.polls.model.CalendarEvent;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

	@Query(
    		value = "SELECT * FROM budget WHERE budget.expense =:expense AND budget.date >=:beginDate AND budget.date <=:endDate ORDER BY budget.date Desc",
    		nativeQuery = true)
    	Page<Budget> findAll(Pageable pageable, boolean expense, String beginDate, String endDate);
	
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM budget WHERE budget.type =:type AND budget.assign_ref =:id",
			nativeQuery = true)
		void deleteAllByTypeAndAssignRef(String type, Long id);
    
}
