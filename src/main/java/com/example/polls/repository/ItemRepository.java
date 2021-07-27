package com.example.polls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Item;
import com.example.polls.model.Student;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

	@Query(
			value = "SELECT * FROM items WHERE items.active =:active AND items.name LIKE %:searchText% AND items.type LIKE %:type%"
					+ " ORDER BY items.type DESC",
			nativeQuery = true)
		Page<Item> findAllBySearchAndType(Pageable pageable, String searchText, String type, boolean active);
	
	@Query(
			value = "SELECT * FROM items WHERE items.active =:active AND items.name LIKE %:searchText%"
					+ " ORDER BY items.type DESC",
			nativeQuery = true)
		Page<Item> findAll(Pageable pageable, String searchText, boolean active);
	
	@Query(
			value = "SELECT * FROM items WHERE items.active =:active AND items.type LIKE :type%"
					+ " ORDER BY items.name DESC",
			nativeQuery = true)
		Page<Item> findAllByType(Pageable pageable, String type, boolean active);
	
		/*
		 * @Query( value =
		 * "SELECT * FROM items WHERE items.active = true AND items.type LIKE %:searchText% OR items.name LIKE %:searchText% OR items.description LIKE %:searchText%"
		 * , nativeQuery = true) Page<Item> findAll(Pageable pageable, String
		 * searchText);
		 */
	
	@Query(
			value = "SELECT * FROM items WHERE items.active =:active ORDER BY items.type DESC",
			nativeQuery = true)
		Page<Item> findAllByActive(Pageable pageable, boolean active);

}