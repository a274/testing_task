package ru.a274.groupdirapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.a274.groupdirapp.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, String> {
    Optional<Group> findById(String id);
}