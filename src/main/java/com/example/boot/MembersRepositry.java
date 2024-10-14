package com.example.boot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepositry extends JpaRepository<Members, Integer> {
}
