package com.example.testapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "link")
@Data
@NoArgsConstructor
public class Link {
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

    public Link(String email, Integer testId, String randomLink) {
        this.email = email;
        this.testId = testId;
        this.randomLink = randomLink;
        this.rightAnswersCount = -1;
    }
}
