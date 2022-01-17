package ru.a274.reportdirapp.model.request;

import lombok.Getter;
import lombok.Setter;
import ru.a274.reportdirapp.model.db.User;

import java.util.List;
@Setter
@Getter
public class RequestUserReport {
    private String id;
    private String login;
    private String email;
    private String status;
    private List<RequestReportBody> reports;

    public RequestUserReport(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.reports = user.getReportList().stream()
                .map(x -> new RequestReportBody(x.getReportTitle(), x.getReport())).toList();
    }
}
