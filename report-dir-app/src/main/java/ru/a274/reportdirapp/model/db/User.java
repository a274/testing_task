package ru.a274.reportdirapp.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "user_")
public class User {

    @Id
    @Column(name = "user_id", length = 10, nullable = false, updatable = false)
    private String id;

    @Column(name = "user_login")
    @javax.validation.constraints.NotNull
    private String login;

    @Column(name = "user_email")
    @Email
    @javax.validation.constraints.NotNull
    private String email;

    @Column(name = "user_status")
    @javax.validation.constraints.NotNull
    private String status;

    @Column(name = "user_password")
    private String password;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private List<Report> reportList;
}