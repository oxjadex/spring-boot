package com.example.boot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepositry extends JpaRepository<Members, Integer> {
    Optional<Members> findByUsername(String username);
}
