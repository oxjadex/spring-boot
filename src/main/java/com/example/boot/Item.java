package com.example.boot;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {
    @Id
//    primary key로
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer price;
    private String username;
}

