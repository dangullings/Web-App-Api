package com.example.polls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.ClassDate;
import com.example.polls.model.ClassSession;
import com.example.polls.model.CalendarEvent;

@Repository
public interface EventRepo extends JpaRepository<CalendarEvent, Long> {

	@Query(
    		value = "SELECT * FROM calendar_events WHERE calendar_events.month =:month AND calendar_events.year =:year",
    		nativeQuery = true)
    	Page<CalendarEvent> findAllByMonthYear(Pageable pageable, String month, String year);
	
	@Query(
    		value = "SELECT * FROM calendar_events ORDER BY calendar_events.date Asc LIMIT 10",
    		nativeQuery = true)
    	List<CalendarEvent> findAllByDateAsc();
	
	@Query(
    		value = "SELECT * FROM calendar_events ORDER BY calendar_events.date Desc LIMIT 10",
    		nativeQuery = true)
    	List<CalendarEvent> findAllByDateDesc();
}
