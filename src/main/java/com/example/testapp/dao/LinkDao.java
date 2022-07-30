package com.example.testapp.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "link")
@Data
public class LinkDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "test_id")
    private Integer testId;
    @Column(name = "random_link")
    private String randomLink;
    @Column(name = "right_answers_count")
    private Integer rightAnswersCount;
}
