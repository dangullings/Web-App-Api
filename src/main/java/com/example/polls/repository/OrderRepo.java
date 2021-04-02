package com.example.polls.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Order;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {
	
	@Query(
    		value = "SELECT * FROM orders o ORDER BY o.date DESC",
    		nativeQuery = true)
    	Page<Order> findAllOrderByDate(Pageable pageable);
}