package com.example.polls.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.LineItem;

@Repository
public interface LineItemRepo extends PagingAndSortingRepository<LineItem, Long> {


}