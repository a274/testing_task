package ru.a274.userdirapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.a274.userdirapp.model.User;
import ru.a274.userdirapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email")
    public ResponseEntity<Object> getUsersByEmails(@RequestParam("email") List<String> emails) {
        List<User> users = new ArrayList<>();
        for (String email : emails) {
            User user = userService.getUserByEmail(email);
            if (user == null)
                return ResponseEntity.badRequest().body(new ResponseMessage("User not found for email: " + email));
            users.add(user);
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersByLogins(@RequestParam("login") List<String> logins) {
        List<User> users = new ArrayList<>();
        for (String login : logins) {
            User user = userService.getUserByLogin(login);
            if (user == null)
                return ResponseEntity.badRequest().body(new ResponseMessage("User not found for login: " + login));
            users.add(user);
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/id")
    public ResponseEntity<Object> getUsersByIds(@RequestParam("id") List<String> ids) {
        List<User> users = new ArrayList<>();
        for (String id : ids) {
            User user = userService.getUserById(id);
            if (user == null)
                return ResponseEntity.badRequest().body(new ResponseMessage("User not found for id: " + id));
            users.add(user);
        }
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postController(
            @RequestBody User user) {
        log.info("user is being created " + user.getLogin());
        userService.create(user.getLogin(), user.getEmail(), user.getStatus(), user.getPassword());
        return ResponseEntity.ok().body(user);
    }

}
