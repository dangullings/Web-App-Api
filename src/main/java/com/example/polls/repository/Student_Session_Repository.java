package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.CalendarEvent_Student;
import com.example.polls.model.Student_Session;

public interface Student_Session_Repository extends PagingAndSortingRepository<Student_Session, Long> {
	Optional<Student_Session> findById(Long studentSessionId);
	
	@Query(
			value = "SELECT * FROM class_session_students css WHERE css.student_id =:studentId",
			nativeQuery = true)
		List<Student_Session> findAllByStudentId(@Param("studentId") Long studentId);
		
		@Query(
				value = "SELECT student_id FROM class_session_students css WHERE css.class_session_id =:sessionId",
				nativeQuery = true)
			List<Long> findAllBySessionId(@Param("sessionId") long sessionId);
		
		@Query(
				value = "SELECT * FROM class_session_students css WHERE css.class_session_id =:sessionId",
				nativeQuery = true)
		List<Student_Session> findAllSessionsBySessionId(@Param("sessionId") Long sessionId);
		
		@Modifying
	    @Transactional
		@Query(
				value = "DELETE FROM class_session_students css WHERE css.student_id =:studentId",
				nativeQuery = true)
			void deleteAllByStudentId(@Param("studentId") Long studentId);
		
		@Modifying
	    @Transactional
		@Query(
				value = "DELETE FROM class_session_students css WHERE css.class_session_id =:sessionId",
				nativeQuery = true)
			void deleteAllBySessionId(@Param("sessionId") Long sessionId);
}
