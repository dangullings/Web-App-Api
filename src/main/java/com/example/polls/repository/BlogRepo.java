package com.example.polls.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Blog;

@Repository
public interface BlogRepo extends PagingAndSortingRepository<Blog, Long> {

}
