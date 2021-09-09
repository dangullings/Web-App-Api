package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CalendarEvent;
import com.example.polls.model.ClassDate;
import com.example.polls.model.ClassSession;

@Repository
public interface ClassSessionRepo extends JpaRepository<ClassSession, Long> {
    Optional<ClassSession> findById(Long classSessionId);
    
    @Query(
    		value = "SELECT * FROM class_sessions WHERE class_sessions.month =:month AND class_sessions.year =:year",
    		nativeQuery = true)
    	Page<ClassSession> findAllByMonthYear(Pageable pageable, String month, String year);
    
    @Query(
    		value = "SELECT * FROM class_sessions ORDER BY class_sessions.start_date DESC LIMIT 10",
    		nativeQuery = true)
    	List<ClassSession> findAllByDateDesc();
    
    @Query(
    		value = "SELECT * FROM class_sessions ORDER BY class_sessions.start_date Asc LIMIT 10",
    		nativeQuery = true)
    	List<ClassSession> findAllByDateAsc();
    
    @Query(
    		value = "SELECT * FROM class_sessions ORDER BY class_sessions.end_date DESC",
    		nativeQuery = true)
    	Page<ClassSession> findAllByEndDate(Pageable pageable);
}
