package com.example.polls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Budget;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    
    
}
