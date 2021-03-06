package ru.a274.userdirapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "user_")
public class User {

    @Id
    @Column(name = "user_id", length = 10, nullable = false, updatable = false)
    private String id;

    @Column(name = "user_login")
    @NotNull
    private String login;

    @Column(name = "user_email")
    @Email
    @NotNull
    private String email;

    @Column(name = "user_status")
    @NotNull
    private String status;

    @Column(name = "user_password")
    private String password;
}
