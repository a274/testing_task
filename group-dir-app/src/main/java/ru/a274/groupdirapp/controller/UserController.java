package ru.a274.groupdirapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.a274.groupdirapp.model.User;
import ru.a274.groupdirapp.service.UserService;

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

    @GetMapping
    public ResponseEntity<Object> getUsersByIds(@RequestParam("id") List<String> ids) {
        List<User> users = new ArrayList<>();
        for (String userId : ids) {
            User user = userService.getUserById(userId);
            if (user == null)
                return ResponseEntity.badRequest().body(new ResponseError("User not found for id: " + userId));
            users.add(user);
        }
        return ResponseEntity.ok().body(users);
    }

    /*
    @GetMapping("/disable")
    public ResponseEntity<Object> disableUser(@RequestParam("id") String userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseError("User not found for id " + userId));
        }
        userService.disableUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/enable")
    public ResponseEntity<Object> enableUser(@RequestParam("id") String userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ResponseError("User not found for id " + userId));
        }
        userService.enableUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postController(
            @RequestBody User user) {
        log.info("user is being created " + user.getLogin());
        userService.create(user.getLogin(), user.getEmail(), user.getStatus());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/fill")
    public ResponseEntity<Object> fill() {
        userService.fillDB();
        return ResponseEntity.ok().body(new ResponseError("users created"));
    }

     */

}
