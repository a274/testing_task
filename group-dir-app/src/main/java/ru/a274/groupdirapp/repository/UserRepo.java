package ru.a274.groupdirapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.a274.groupdirapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findByLogin(String login);
    User findByEmail(String email);
    void deleteById(String id);
}