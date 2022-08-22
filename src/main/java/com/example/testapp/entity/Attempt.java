package com.example.testapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "attempt")
@Data
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "option_id")
    private Integer optionId;
    @Column(name = "link_id")
    private Integer linkId;
}
