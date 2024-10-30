package com.example.wishlink_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wishlink_backend.User;

@Repository
public interface UserRepository  extends JpaRepository<User , Integer> {
    public Optional<User> getUserIdByUsername(String username);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
