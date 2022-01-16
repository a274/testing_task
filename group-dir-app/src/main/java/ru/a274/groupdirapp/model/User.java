package ru.a274.groupdirapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "user_")
public class User {

    @Column(name = "user_login")
    @NotNull
    private String login;

    @Id
    @Column(name = "user_email")
    @Email
    @NotNull
    private String email;

    @Column(name = "user_status")
    @NotNull
    private String status;
}
