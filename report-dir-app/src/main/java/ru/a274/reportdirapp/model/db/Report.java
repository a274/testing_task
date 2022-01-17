package ru.a274.reportdirapp.model.db;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "report_list")
public class Report {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "report_title")
    private String reportTitle;

    @Column(name = "report")
    private String report;

    @Column(name = "user_id")
    private String userId;
}
