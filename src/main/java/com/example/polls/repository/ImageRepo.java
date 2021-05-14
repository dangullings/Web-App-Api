package com.example.polls.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Image;

@Repository
public interface ImageRepo extends PagingAndSortingRepository<Image, Long> {

	MultipartFile save(MultipartFile file);

}
