package ru.a274.userdirapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Table(name = "user_")
public class User {
    @Id
    @Column(name = "user_id", length = 10, nullable = false, updatable = false)
    private String id;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_email")
    @Email
    private String email;

    @Column(name = "user_status")
    private String status;
}