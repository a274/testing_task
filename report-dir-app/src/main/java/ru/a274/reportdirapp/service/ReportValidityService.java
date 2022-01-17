package ru.a274.reportdirapp.service;

import org.springframework.stereotype.Service;
import ru.a274.reportdirapp.model.request.RequestReport;
@Service
public class ReportValidityService {
    private RequestReport report;
    private String message = "";

    public void setReport(RequestReport report) {
        this.report = report;
    }

    public boolean isValid() {
        message = "";
        if (report.getUserId() == null) {
            message = "No user id";
            return false;
        }
        if (report.getGroups() == null) {
            message = "No group list";
            return false;
        }
        if (report.getGroups().isEmpty()) {
                message = "No group";
                return false;
        }
        if (report.getReports() == null) {
            message = "No report list";
            return false;
        }
        if (report.getReports().isEmpty()) {
            message = "No report";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }
}
