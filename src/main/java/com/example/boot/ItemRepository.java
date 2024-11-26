package com.example.boot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findPageBy(Pageable page);

    @Query(value = "SELECT * FROM boot.item WHERE MATCH(title) AGAINST(?1)",  nativeQuery = true)
    List<Item> fullTextSearch(String text);
}
