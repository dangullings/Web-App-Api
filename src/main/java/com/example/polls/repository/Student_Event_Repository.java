package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.CalendarEvent_Student;
import com.example.polls.model.Test_Student;

public interface Student_Event_Repository extends PagingAndSortingRepository<CalendarEvent_Student, Long> {
	Optional<CalendarEvent_Student> findById(Long studentSessionId);
	
	@Query(
			value = "SELECT * FROM calendar_events_students WHERE calendar_events_students.student_id =:studentId",
			nativeQuery = true)
		List<CalendarEvent_Student> findAllByStudentId(Long studentId);
		
		@Query(
				value = "SELECT * FROM calendar_events_students WHERE calendar_events_students.calendar_event_id =:eventId",
				nativeQuery = true)
		List<CalendarEvent_Student> findAllByEventId(Long eventId);
		
		@Query(
				value = "SELECT student_id FROM calendar_events_students WHERE calendar_events_students.calendar_event_id =:eventId",
				nativeQuery = true)
			List<Long> findAllByCalendarEventId(Long eventId);
		
		@Modifying
	    @Transactional
		@Query(
				value = "DELETE FROM calendar_events_students WHERE calendar_events_students.calendar_event_id =:eventId AND calendar_events_students.student_id =:studentId",
				nativeQuery = true)
			void deleteAllByEventIdAndStudentId(Long eventId, Long studentId);
		
		@Modifying
	    @Transactional
		@Query(
				value = "DELETE FROM calendar_events_students WHERE calendar_events_students.calendar_event_id =:eventId",
				nativeQuery = true)
			void deleteAllByEventId(Long eventId);
		
		@Modifying
	    @Transactional
		@Query(
				value = "DELETE FROM calendar_events_students WHERE calendar_events_students.student_id =:studentId",
				nativeQuery = true)
			void deleteAllByStudentId(Long studentId);
}
