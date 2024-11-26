package com.example.boot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//CommentRepository.java
public interface CommentRepository extends JpaRepository<comment, Integer> {
    List<comment> findAllByParentId(Integer a);
}
