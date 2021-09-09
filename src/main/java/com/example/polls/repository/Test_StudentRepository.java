package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.ClassSession;
import com.example.polls.model.Test_Student;

@Repository
public interface Test_StudentRepository extends PagingAndSortingRepository<Test_Student, Long> {
    Optional<Test_Student> findById(Long test_studentId);
    
	@Query(
			value = "SELECT * FROM test_student WHERE test_student.test_id =:testId and test_student.student_id =:studentId",
			nativeQuery = true)
		Test_Student findByTestIdAndStudentId(Long testId, Long studentId);
	
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM test_student WHERE test_student.test_id =:testId",
			nativeQuery = true)
		void deleteAllByTestId(Long testId);
	
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM test_student WHERE test_student.student_id =:studentId",
			nativeQuery = true)
		void deleteAllByStudentId(Long studentId);
	
	@Modifying
    @Transactional
	@Query(
			value = "DELETE FROM test_student WHERE test_student.test_id =:testId AND test_student.student_id =:studentId",
			nativeQuery = true)
		void deleteAllByTestIdAndStudentId(Long testId, Long studentId);
		
	@Query(
			value = "SELECT * FROM test_student WHERE test_student.test_id =:testId",
			nativeQuery = true)
		List<Test_Student> findAllTestStudentsByTestId(Long testId);
		
	@Query(
			value = "SELECT * FROM test_student WHERE test_student.student_id =:studentId",
			nativeQuery = true)
		Page<Test_Student> findAllByStudentId(Pageable pageable, Long studentId);
		
		@Query(
	    		value = "SELECT student_id FROM test_student WHERE test_student.test_id =:testId",
	    		nativeQuery = true)
	    	List<Long> findAllByTestId(Long testId);

		@Query(
	    		value = "SELECT test_id FROM test_student WHERE test_student.student_id =:studentId",
	    		nativeQuery = true)
	    	List<Long> findAllByStudentId(Long studentId);
}
