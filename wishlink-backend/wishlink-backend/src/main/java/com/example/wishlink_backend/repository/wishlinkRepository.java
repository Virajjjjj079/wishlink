package com.example.wishlink_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wishlink_backend.Link;


@Repository
public interface wishlinkRepository extends JpaRepository<Link , Integer> {
   List<Link> findByUserId(Integer userId);
   List<Link> findByCategory(String category);
   List<Link> findByCategoryAndUserId(String category, Integer userId);
}
