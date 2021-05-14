package com.example.polls.service;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Image;
import com.example.polls.repository.ImageRepo;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepository;

	public Image findById(Long id) {
		return imageRepository.findById(id).get();
	}

	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			imageRepository.deleteById(id);
			jsonObject.put("message", "Item deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	public Image saveOrUpdate(MultipartFile file, Long id) {
		Image item = new Image();
		item.setName(file.getOriginalFilename());
		try {
			item.setPhoto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		item.setId(id);
		return imageRepository.save(item);
	}
}