package ru.a274.groupdirapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.a274.groupdirapp.model.Email;
import ru.a274.groupdirapp.model.Group;
import ru.a274.groupdirapp.model.Status;
import ru.a274.groupdirapp.repository.GroupRepo;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class GroupService {
    private final GroupRepo groupRepo;

    @Autowired
    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    @Transactional
    public void create(String status) {
        Group group = new Group();
        String newId = GroupGenerator.newId();
        group.setId(newId);
        group.setEmail(generateEmailList(newId));
        group.setStatus(status);
        group.setCreationDate(Date.from(Instant.now()));
        group.setModificationDate(Date.from(Instant.now()));
        log.info(String.format("New group: %s %s %s %s", group.getId(),
                Arrays.toString(group.getEmail().stream().map(Email::getEmail).toArray()),
                group.getStatus(), group.getCreationDate().toString()));
        groupRepo.save(group);
    }

    @Transactional(readOnly = true)
    public Group getGroupById(String id) {
        Optional<Group> group = groupRepo.findById(id);
        return group.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Group> getAll() {
        return groupRepo.findAll();
    }

    @Transactional
    public void saveGroup(Group group) {
        groupRepo.save(group);
    }

    @Transactional
    public void deleteGroup(String id) {
        Group group = getGroupById(id);
        if (isDeleted(id)) return;
        group.setStatus(Status.INACTIVE.name());
        group.setDeletionDate(Date.from(Instant.now()));
        groupRepo.save(group);
    }

    @Transactional
    public void disableGroup(String id) {
        Group group = getGroupById(id);
        if (group == null) return;
        if (isDeleted(id)) return;
        group.setStatus(Status.INACTIVE.name());
        group.setModificationDate(Date.from(Instant.now()));
        log.info("group " + group.getId() + " disabled on: " + group.getModificationDate().toString());
        groupRepo.save(group);
    }

    @Transactional
    public void enableGroup(String id) {
        Group group = getGroupById(id);
        if (group == null) return;
        if (isDeleted(id)) return;
        group.setStatus(Status.ACTIVE.name());
        group.setModificationDate(Date.from(Instant.now()));
        log.info("group " + group.getId() + " enabled on: " + group.getModificationDate().toString());
        groupRepo.save(group);
    }

    @Transactional
    @Scheduled(fixedRateString = "${service.fill-db-every5min}")
    public void fillDB() {
        for (int i = 0; i < 10; i++) {
            create(Status.ACTIVE.name());
        }
    }

    @Scheduled(cron = "${service.cron.everyday10pm}", zone = "${service.cron.zone}")
    public void scheduledDisabling() {
        List<Group> allGroups = groupRepo.findAll();
        for (Group group : allGroups) {
            disableGroup(group.getId());
        }
    }

    private List<Email> generateEmailList(String groupId) {
        List<Email> emailList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Email email = new Email();
            email.setGroup(groupId);
            email.setEmail(GroupGenerator.generateEmail());
            emailList.add(email);
        }
        return emailList;
    }


    private boolean isDeleted(String id) {
        Group group = getGroupById(id);
        if (group == null) return true;
        return group.getDeletionDate() != null;
    }
}