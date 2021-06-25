package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Student;
import com.example.polls.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    Optional<User> findByPasswordResetToken(String passwordResetToken);
    
    Optional<User> findByEmail(String email);
    
    @Query(
    		value = "SELECT * FROM users u WHERE u.email LIKE :%email%",
    		nativeQuery = true)
    	Optional<User> findUserByEmail(String email);
}