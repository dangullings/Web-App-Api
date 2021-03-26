package com.example.polls.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Order;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {


}