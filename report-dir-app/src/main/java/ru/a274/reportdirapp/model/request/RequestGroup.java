package ru.a274.reportdirapp.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RequestGroup {
    private String id;

    private List<String> email;

    private String status;

    private Date creationDate;

    private Date modificationDate;

    private Date deletionDate;

}
