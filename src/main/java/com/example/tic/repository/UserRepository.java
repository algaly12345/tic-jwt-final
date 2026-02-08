package com.example.tic.repository;

import com.example.tic.entity.User;
import com.example.tic.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNationalId(String nationalId);
    boolean existsByEmail(String email);
    boolean existsByNationalId(String nationalId);
}
