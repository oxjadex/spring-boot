package com.example.boot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<comment, Long> {
    List<comment> findAllByParentId(Integer parentId);
}
