package ru.a274.reportdirapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.a274.reportdirapp.model.db.Report;
import ru.a274.reportdirapp.model.db.User;
import ru.a274.reportdirapp.model.request.RequestReport;
import ru.a274.reportdirapp.model.request.RequestReportBody;
import ru.a274.reportdirapp.model.request.RequestUserReport;
import ru.a274.reportdirapp.repository.ReportRepo;
import ru.a274.reportdirapp.repository.UserRepo;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final ReportRepo reportRepo;

    @Autowired
    public UserService(UserRepo userRepo, ReportRepo reportRepo) {
        this.userRepo = userRepo;
        this.reportRepo = reportRepo;
    }

    public RequestUserReport getUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) return null;
        return new RequestUserReport(user.get());
    }

    public void saveUserReport(RequestReport report) {
        for (RequestReportBody reportItem : report.getReports()) {
            Report newReport = new Report();
            newReport.setReportTitle(reportItem.getTitle());
            newReport.setReport(reportItem.getContent());
            newReport.setUserId(getUserById(report.getUserId()).getId());
            reportRepo.save(newReport);
        }
    }



}
