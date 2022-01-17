package ru.a274.reportdirapp.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class RequestReport {
    private String userId;
    private List<RequestReportBody> reports;
    private List<String> groups;
}
