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
import com.example.polls.model.Student;
import com.example.polls.repository.LineItemRepo;
import com.example.polls.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;
    
    @Autowired
	private LineItemRepo lineItemRepo;
    
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	public Page<Order> findAllOrderByDate(Pageable pageable) {
		return orderRepository.findAllOrderByDate(pageable);
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}
	
	public Order saveOrUpdate(Order student) {
		return orderRepository.save(student);
	}

	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			orderRepository.deleteById(id);
			jsonObject.put("message", "Item deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}