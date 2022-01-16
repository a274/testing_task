package ru.a274.groupdirapp.controller;


import lombok.Getter;
import lombok.Setter;
import ru.a274.groupdirapp.model.Email;
import ru.a274.groupdirapp.model.Group;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ResponseGroup {
    private String id;

    private List<String> email;

    private String status;

    private Date creationDate;

    private Date modificationDate;

    private Date deletionDate;

    public ResponseGroup(Group group) {
        this.id = group.getId();
        this.email = group.getEmail().stream().map(Email::getEmail).toList();
        this.status = group.getStatus();
        this.creationDate = group.getCreationDate();
        this.modificationDate = group.getModificationDate();
        this.deletionDate = group.getDeletionDate();
    }

}
