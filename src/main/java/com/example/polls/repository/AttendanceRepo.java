package com.example.polls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findById(Long attendanceId);
    
	@Query(
			value = "SELECT * FROM attendance WHERE attendance.class_date_id =:classDateId and attendance.student_id =:studentId",
			nativeQuery = true)
		Attendance findByClassDateIdAndStudentId(Long classDateId, Long studentId);
	
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM attendance WHERE attendance.student_id =:studentId",
			nativeQuery = true)
		void deleteAllByStudentId(Long studentId);
}
