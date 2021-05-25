package com.api.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.api.config.EmailCfg;
import com.api.services.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@Service
@EnableAsync
public class MailServiceImpl implements MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    private JavaMailSenderImpl mailSender;

    public MailServiceImpl() {
        var emailCfg = new EmailCfg();
        mailSender = emailCfg.javaMailSender();
    }

    @Async
    @Scheduled(cron = "${properties.scheduled.auto-send-welcome-email-cron}")
    public void autoSendEmail() {
        var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        var now = dtf.format(LocalDateTime.now());
        LOG.info("Send welcomme mail at " + now);
        sendSimpleMail("goog11@lool.io", "9d324a0e27-38839d@inbox.mailtrap.io", "Bienvenue!",
                "Test d'envoie auto de message de bienvenue");
    }

    public void sendSimpleMail( String from, String to, String subject, String content, String... cc) throws MailException  {
        sendSimpleMail( from, new String[] {to}, subject, content, cc);
    }

    public void sendSimpleMail( String from, String[] to, String subject, String content, String... cc) throws MailException  {

    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom(from);
    	message.setTo(to);
    	message.setSubject(subject);
    	message.setText(content);

        try {
            mailSender.send(message);
            LOG.info("A simple email has been sent. ");
        } catch (Exception e) {
            LOG.error("An exception occurred while sending a simple email! ", e);
        }
    }

}