package ru.a274.reportdirapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.a274.reportdirapp.model.request.AuthUser;
import ru.a274.reportdirapp.model.request.RequestReport;
import ru.a274.reportdirapp.service.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class PostReportValidator {
    private final UserService userService;
    private final GroupValidityService groupValidityService;
    private final ReportValidityService reportValidityService;
    private final GroupEmailService groupEmailService;
    private final UserAuthService userAuthService;
    private List<String> emailList = new ArrayList<>();

    @Autowired
    public PostReportValidator(UserService userService, GroupValidityService groupValidityService,
                               ReportValidityService reportValidityService, GroupEmailService groupEmailService,
                               UserAuthService userAuthService) {
        this.userService = userService;
        this.groupValidityService = groupValidityService;
        this.reportValidityService = reportValidityService;
        this.groupEmailService = groupEmailService;
        this.userAuthService = userAuthService;
    }

    public ResponseEntity<Object> isUserAuthenticated(Authentication authentication) {
        AuthUser authUser = (AuthUser) userAuthService.loadUserByUsername(authentication.getName());
        if (!userAuthService.getMessage().isEmpty())
            return ResponseEntity.badRequest().body(userAuthService.getMessage());
        if (!authUser.isEnabled())
            return ResponseEntity.badRequest().body("User is inactive for login: " + authentication.getName());
        return null;
    }

    public ResponseEntity<Object> isReportValid(RequestReport report) {
        reportValidityService.setReport(report);
        if (!reportValidityService.isValid())
            return ResponseEntity.badRequest().body(reportValidityService.getMessage());
        return null;
    }

    public ResponseEntity<Object> isUserInDirectory(RequestReport report) {
        AuthUser authUser1 = (AuthUser) userAuthService.loadUserById(report.getUserId());
        if (authUser1 == null || !userAuthService.getMessage().isEmpty())
            return ResponseEntity.badRequest().body(userAuthService.getMessage());
        if (!authUser1.isEnabled())
            return ResponseEntity.badRequest().body("User is inactive for id: " + report.getUserId());
        return null;
    }

    public ResponseEntity<Object> areGroupsValid(RequestReport report) {
        for (String group : report.getGroups()) {
            String message = groupValidityService.isGroupValid(group);
            if (!message.isEmpty()) return ResponseEntity.badRequest().body(message);
            this.emailList.addAll(groupValidityService.getEmailList());
        }
        return null;
    }

    public List<String> getEmailList() {
        return this.emailList;
    }

    public void saveReport(RequestReport report) {
        userService.saveUserReport(report);
    }

    public void sendEmailsToGroups(List<String> emailList, RequestReport report) {
        try {
            String emailMessage = "New report added by user: " + report.getUserId();
            groupEmailService.sendToGroup(emailList, emailMessage, emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
