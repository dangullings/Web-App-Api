package com.example.polls.service;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.polls.model.LineItem;
import com.example.polls.model.Order;
import com.example.polls.repository.LineItemRepo;

@Service
public class LineItemService {

    @Autowired
    private LineItemRepo repo;
    
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    
	public Page<LineItem> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public LineItem findById(Long id) {
		return repo.findById(id).get();
	}
	
	public LineItem saveOrUpdate(LineItem object) {
		return repo.save(object);
	}

	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			repo.deleteById(id);
			jsonObject.put("message", "Item deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	public void deleteByOrderId(Long orderId) {
		repo.deleteByOrderId(orderId);
	}
	
	public List<LineItem> findAllLineItemsById(long orderId) {
		return repo.findAllLineItemsByOrderId(orderId);
	}
}