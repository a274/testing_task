package ru.a274.reportdirapp.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "group_")
public class Group {
    @Id
    @Column(name = "group_id", length = 10, nullable = false, updatable = false)
    private String id;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "group_id")
    private List<Email> email;

    @Column(name = "group_status", nullable = false)
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "modification_date")
    private Date modificationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "deletion_date")
    private Date deletionDate;

}