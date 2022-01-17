package ru.a274.reportdirapp.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.a274.reportdirapp.model.request.AuthUser;
import ru.a274.reportdirapp.model.request.RequestReport;
import ru.a274.reportdirapp.model.request.RequestUserReport;
import ru.a274.reportdirapp.model.request.ResponseMessage;
import ru.a274.reportdirapp.service.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportController {
    private final UserService userService;
    private final GroupValidityService groupValidityService;
    private final ReportValidityService reportValidityService;
    private final GroupEmailService groupEmailService;
    private final UserAuthService userAuthService;
    private PostReportValidator postReportValidator;

    @Autowired
    public ReportController(UserService userService, GroupValidityService groupValidityService,
                            ReportValidityService reportValidityService, GroupEmailService groupEmailService,
                            UserAuthService userAuthService, PostReportValidator postReportValidator) {
        this.userService = userService;
        this.groupValidityService = groupValidityService;
        this.reportValidityService = reportValidityService;
        this.groupEmailService = groupEmailService;
        this.userAuthService = userAuthService;
        this.postReportValidator = postReportValidator;
    }

    @PostMapping
    public ResponseEntity<Object> postReport(Authentication authentication, @RequestBody RequestReport report) {
        /*
        postReportValidator.isUserAuthenticated(authentication);
        postReportValidator.isReportValid(report);
        postReportValidator.isUserInDirectory(report);
        postReportValidator.areGroupsValid(report);
        postReportValidator.saveReport(report);
        postReportValidator.sendEmailsToGroups(postReportValidator.getEmailList(),report);
        
         */

        // валидация пользователя
        AuthUser authUser = (AuthUser) userAuthService.loadUserByUsername(authentication.getName());
        if (!userAuthService.getMessage().isEmpty())
            return ResponseEntity.badRequest().body(userAuthService.getMessage());
        if (!authUser.isEnabled())
            return ResponseEntity.badRequest().body("User is inactive for login: " + authentication.getName());

        //if any_field is null -> respMessage (request is not valid)
        reportValidityService.setReport(report);
        if (!reportValidityService.isValid())
            return ResponseEntity.badRequest().body(reportValidityService.getMessage());

        // валидация пользователя в отчете
        AuthUser authUser1 = (AuthUser) userAuthService.loadUserById(report.getUserId());
        if (authUser1 == null || !userAuthService.getMessage().isEmpty())
            return ResponseEntity.badRequest().body(userAuthService.getMessage());
        if (!authUser1.isEnabled())
            return ResponseEntity.badRequest().body("User is inactive for id: " + report.getUserId());

        // валидация групп
        List<String> emailList = new ArrayList<>();
        for (String group : report.getGroups()) {
            String message = groupValidityService.isGroupValid(group);
            if (!message.isEmpty()) return ResponseEntity.badRequest().body(message);
            emailList.addAll(groupValidityService.getEmailList());
        }
        // сохранение отчета
        userService.saveUserReport(report);

        // отправка писем
        try {
            String emailMessage = "New report added by user: " + report.getUserId();
            groupEmailService.sendToGroup(emailList, emailMessage, emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(report);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserInfoById(Authentication authentication, @PathVariable("id") String userId) {
        AuthUser authUser = (AuthUser) userAuthService.loadUserByUsername(authentication.getName());
        if (!userAuthService.getMessage().isEmpty())
            return ResponseEntity.badRequest().body(userAuthService.getMessage());
        if (!authUser.isEnabled())
            return ResponseEntity.badRequest().body("User is inactive for login: " + authentication.getName());

        RequestUserReport user = userService.getUserById(userId);
        if (user == null)
            return ResponseEntity.badRequest().body(new ResponseMessage("User not found for id: " + userId));
        return ResponseEntity.ok().body(user);
    }


}