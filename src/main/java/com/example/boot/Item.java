package com.example.boot;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "인덱스이름작명", columnList = "인덱스만들컬럼명1"),
        @Index(name = "인덱스이름작명", columnList = "인덱스만들컬럼명2")
})
public class Item {
    @Id
//    primary key로
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer price;
    private String username;
    private String image;
}

