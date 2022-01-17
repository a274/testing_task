package ru.a274.reportdirapp.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "email_list")

public class Email {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_id")
    private String group;

    @Column(name = "email")
    private String email;
}