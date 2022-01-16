package ru.a274.userdirapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.a274.userdirapp.model.Status;
import ru.a274.userdirapp.model.User;
import ru.a274.userdirapp.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public void create(String login, String email, String status) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setStatus(status);
        log.info(String.format("New user: %s %s %s", user.getLogin(), user.getEmail(), user.getStatus()));
        userRepo.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Optional<User> user = userRepo.findById(email);
        return user.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Transactional
    public void disableUser(String email) {
        User user = getUserByEmail(email);
        user.setStatus(Status.INACTIVE.name());
        userRepo.save(user);
    }

    @Transactional
    public void enableUser(String email) {
        User user = getUserByEmail(email);
        user.setStatus(Status.ACTIVE.name());
        userRepo.save(user);
    }

    @Transactional
    @Scheduled(fixedRateString = "${service.fill-db-every5min}")
    public void fillDB() {
        for (int i = 0; i < 10; i++) {
            create(UserGenerator.newLogin(), UserGenerator.newEmail(), Status.ACTIVE.name());
        }
    }

    @Scheduled(cron = "${service.cron.everyday10pm}", zone = "${service.cron.zone}")
    public void scheduledDisabling() {
        List<User> allUsers = userRepo.findAll();
        for (User user : allUsers) {
            disableUser(user.getEmail());
        }
        log.info("users disabled");
    }

}