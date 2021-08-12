package com.example.polls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Blog;
import com.example.polls.model.Student;

@Repository
public interface BlogRepo extends PagingAndSortingRepository<Blog, Long> {

	@Query(
			value = "SELECT * FROM blogs WHERE blogs.active =:active ORDER BY blogs.date DESC",
			nativeQuery = true)
		Page<Blog> findAllByActive(Pageable pageable, boolean active);
	
}
