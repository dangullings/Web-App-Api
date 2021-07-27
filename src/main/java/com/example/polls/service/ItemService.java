package com.example.polls.service;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.polls.model.Image;
import com.example.polls.model.Item;
import com.example.polls.model.Student;
import com.example.polls.repository.ImageRepo;
import com.example.polls.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ImageRepo imageRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public Page<Item> findAllBySearchAndType(Pageable pageable, String searchText, String type, boolean active) {
		return itemRepository.findAllBySearchAndType(pageable, searchText, type, active);
	}
    
    public Page<Item> findAll(Pageable pageable, String searchText, boolean active) {
		return itemRepository.findAll(pageable, searchText, active);
	}
    
    public Page<Item> findAllByType(Pageable pageable, String type, boolean active) {
		return itemRepository.findAllByType(pageable, type, active);
	}
    
    public Page<Item> findAllByActive(Pageable pageable, boolean active) {
		return itemRepository.findAllByActive(pageable, active);
	}
    
	/*
	 * public Page<Item> findAll(Pageable pageable, String searchText) {
	 * System.out.println("findAll items service"+searchText); return
	 * itemRepository.findAll(pageable, searchText); }
	 */
    
	public Page<Item> findAll(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	public Item findById(Long id) {
		return itemRepository.findById(id).get();
	}

	public Image findImageById(Long id) {
		System.out.print("findImageId ----------------------------"+id);
		return imageRepository.findById(id).get();
	}
	
	public Item saveOrUpdate(Item student) {
		return itemRepository.save(student);
	}

	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			itemRepository.deleteById(id);
			jsonObject.put("message", "Item deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	public String deleteItemImageById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			imageRepository.deleteById(id);
			jsonObject.put("message", "Image deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	/*
	 * public Page<Item> findAllItemsByActive(Pageable pageable) {
	 * System.out.println("findallbyActive"); return
	 * itemRepository.findAllItemsByActive(pageable); }
	 */

	public Image saveOrUpdateImage(MultipartFile file, Long id) {
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