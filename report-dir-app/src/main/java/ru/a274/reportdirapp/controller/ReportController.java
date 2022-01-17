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


@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportController {
    private final UserService userService;
    private final UserAuthService userAuthService;
    private final PostReportValidator postReportValidator;

    @Autowired
    public ReportController(UserService userService, UserAuthService userAuthService,
                            PostReportValidator postReportValidator) {
        this.userService = userService;
        this.userAuthService = userAuthService;
        this.postReportValidator = postReportValidator;
    }

    @PostMapping
    public ResponseEntity<Object> postReport(Authentication authentication, @RequestBody RequestReport report) {
        ResponseEntity<Object> responseEntity;
        responseEntity = postReportValidator.isUserAuthenticated(authentication);
        if (responseEntity != null) return responseEntity;
        responseEntity = postReportValidator.isReportValid(report);
        if (responseEntity != null) return responseEntity;
        responseEntity = postReportValidator.isUserInDirectory(report);
        if (responseEntity != null) return responseEntity;
        responseEntity = postReportValidator.areGroupsValid(report);
        if (responseEntity != null) return responseEntity;
        postReportValidator.saveReport(report);
        postReportValidator.sendEmailsToGroups(postReportValidator.getEmailList(), report);
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