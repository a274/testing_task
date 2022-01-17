package ru.a274.reportdirapp.service;


import org.junit.jupiter.api.BeforeAll;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.a274.reportdirapp.model.db.Report;
import ru.a274.reportdirapp.model.db.Status;
import ru.a274.reportdirapp.model.db.User;
import ru.a274.reportdirapp.model.request.RequestReport;
import ru.a274.reportdirapp.model.request.RequestReportBody;
import ru.a274.reportdirapp.repository.ReportRepo;
import ru.a274.reportdirapp.repository.UserRepo;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<Report> captor;

    @Mock
    private UserRepo userRepo;
    @Mock
    private ReportRepo reportRepo;
    private static User user, user1;
    private static List<Report> reports, reports1;

    @BeforeAll
    static void create() {
        reports = new ArrayList<>();
        reports1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Report report = new Report();
            report.setId(i);
            report.setReportTitle("report title " + i);
            report.setReport("content" + i);
            report.setUserId("someId");
            reports.add(report);

            Report report1 = new Report();
            report1.setId(i);
            report1.setReportTitle("report title " + (i+3));
            report1.setReport("content" + (i+3));
            report1.setUserId("someId1");
            reports1.add(report);
        }

        user = new User();
        user.setId("someId");
        user.setLogin("login1");
        user.setEmail("test@test.com");
        user.setReportList(reports);
        user.setStatus(Status.ACTIVE.name());

        user1 = new User();
        user1.setId("someId2");
        user1.setLogin("login2");
        user1.setEmail("test2@test.com");
        user1.setReportList(reports1);
        user1.setStatus(Status.ACTIVE.name());
    }

    @Test
    void getUserById() {
        Mockito.when(userRepo.findById(anyString())).thenReturn(java.util.Optional.ofNullable(user));
        assertEquals(user, userRepo.findById("someId").get());
    }

    @Test
    void getAll() {
        Mockito.when(userRepo.findAll()).thenReturn(List.of(user, user1));
        assertEquals(2, userRepo.findAll().size());
    }

    @Test
    void saveReport() {
        RequestReport report = new RequestReport();
        report.setUserId(user.getId());
        report.setGroups(List.of("group1"));
        report.setReports(List.of(new RequestReportBody("title", "content")));
        Mockito.when(userRepo.findById(anyString())).thenReturn(java.util.Optional.ofNullable(user));
        userService.saveUserReport(report);
        Mockito.verify(reportRepo).save(captor.capture());
        Report capturedReport = captor.getValue();
        assertEquals("title", capturedReport.getReportTitle());
    }

}