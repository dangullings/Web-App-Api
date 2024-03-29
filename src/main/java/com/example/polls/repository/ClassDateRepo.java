package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.ClassDate;

@Repository
public interface ClassDateRepo extends JpaRepository<ClassDate, Long> {
    Optional<ClassDate> findById(Long classDateId);
    
    @Query(
    		value = "SELECT * FROM class_dates WHERE class_dates.month =:month AND class_dates.year =:year",
    		nativeQuery = true)
    	Page<ClassDate> findAllByMonthYear(Pageable pageable, String month, String year);
    
    @Query(
    		value = "SELECT * FROM class_dates WHERE class_dates.month =:month AND class_dates.year =:year AND class_dates.session_id =:session",
    		nativeQuery = true)
    List<ClassDate> findAllByMonthYearAndSession(String month, String year, long session);
    
    @Query(
    		value = "SELECT * FROM class_dates WHERE class_dates.session_id =:sessionId ORDER BY class_dates.date ASC",
    		nativeQuery = true)
    	List<ClassDate> findAllBySessionId(Long sessionId);
    
    @Modifying
    @Transactional
	@Query(
			value = "DELETE FROM class_dates WHERE class_dates.session_id =:sessionId",
			nativeQuery = true)
		void deleteAllBySessionId(Long sessionId);
}
