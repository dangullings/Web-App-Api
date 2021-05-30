package com.example.polls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.ClassDate;
import com.example.polls.model.LineItem;

@Repository
public interface LineItemRepo extends PagingAndSortingRepository<LineItem, Long> {
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM line_items li WHERE li.order_id =:orderId",
			nativeQuery = true)
		void deleteByOrderId(Long orderId);
	
	@Query(
    		value = "SELECT * FROM line_items li WHERE li.order_id =:orderId",
    		nativeQuery = true)
    	List<LineItem> findAllLineItemsByOrderId(Long orderId);

}
