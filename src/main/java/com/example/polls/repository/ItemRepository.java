package com.example.polls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Item;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

	@Query(
		value = "SELECT * FROM items WHERE items.active = true AND items.type LIKE %:searchText% OR items.name LIKE %:searchText% OR items.description LIKE %:searchText%",
		nativeQuery = true)
	Page<Item> findAll(Pageable pageable, String searchText);
	
	@Query(
		value = "SELECT * FROM items WHERE items.active = true",
		nativeQuery = true)
	Page<Item> findAllItemsByActive(Pageable pageable);

}