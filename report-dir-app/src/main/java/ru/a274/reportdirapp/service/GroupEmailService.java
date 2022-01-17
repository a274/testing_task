package ru.a274.reportdirapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@Slf4j
public class GroupEmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public GroupEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    private void send(String emailTo, String subject, String message) throws MessagingException {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true);
            messageHelper.setTo(emailTo);
            messageHelper.setFrom(username);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
            mailSender.send(mail);
        } catch (MailException ignore) {
            log.info("email not sent to " + emailTo);
        }
    }

    public void sendToGroup(List<String> emailList, String subject, String message) throws MessagingException {
        for (String email : emailList) {
            send(email, subject, message);
        }
    }
}
