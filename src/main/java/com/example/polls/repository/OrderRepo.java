package com.example.polls.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Order;
import com.example.polls.model.Student;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {
	
	@Query(
			value = "SELECT * FROM orders WHERE orders.is_fulfilled =:fulfilled AND orders.note LIKE %:searchText%"
					+ " ORDER BY orders.date DESC",
			nativeQuery = true)
		Page<Order> findAll(Pageable pageable, String searchText, boolean fulfilled);
	
	@Query(
			value = "SELECT * FROM orders WHERE orders.is_fulfilled =:fulfilled ORDER BY orders.date DESC",
			nativeQuery = true)
		Page<Order> findAllByFulfilled(Pageable pageable, boolean fulfilled);
	
	@Query(
    		value = "SELECT * FROM orders ORDER BY orders.date DESC",
    		nativeQuery = true)
    	Page<Order> findAllOrderByDate(Pageable pageable);
	
	@Query(
    		value = "SELECT user_id FROM orders WHERE orders.id =:orderId",
    		nativeQuery = true)
    	List<Long> findAllUsersByOrderId(Long orderId);
}