package ru.a274.reportdirapp.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestReportBody {
    private String title;
    private String content;

    public RequestReportBody(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
