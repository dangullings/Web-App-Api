package com.example.polls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Student;

import antlr.collections.List;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

	@Query(
		value = "SELECT * FROM students WHERE students.active =:active AND students.first_name LIKE :searchText% OR students.last_name LIKE :searchText%"
				+ " ORDER BY students.joined DESC",
		nativeQuery = true)
	Page<Student> findAll(Pageable pageable, String searchText, boolean active);
	
	@Query(
		value = "SELECT * FROM students WHERE students.active =:active ORDER BY students.joined DESC",
		nativeQuery = true)
	Page<Student> findAllByActive(Pageable pageable, boolean active);

}