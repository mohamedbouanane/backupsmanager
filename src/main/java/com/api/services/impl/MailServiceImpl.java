package com.api.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

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
import org.springframework.beans.factory.annotation.Autowired;

@Service
@EnableAsync
public class MailServiceImpl implements MailService {
    private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    //@Autowired
    private EmailCfg emailCfg;

    public MailServiceImpl() {

        // To fix later
        emailCfg = new EmailCfg();
        mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailCfg.getHost());
        mailSender.setPort(emailCfg.getPort());
        mailSender.setUsername(emailCfg.getUsername());
        mailSender.setPassword(emailCfg.getPassword());
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.host", "smtp.mailtrap.io");

    }

    @Async
    @Scheduled(cron = "${properties.scheduled.send-welcome-email-cron}")
    public void autoSendEmail() {
        try {
            var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            var now = dtf.format(LocalDateTime.now());
            LOG.info("Send welcomme mail at " + now);
            sendSimpleMail("goog11@lool.io", "9d324a0e27-38839d@inbox.mailtrap.io", "Bienvenue!",
                    "Test d'envoie auto de message de bienvenue");
            LOG.info("Email sended correctly.");
        } catch (Exception e) {
            LOG.error("Error sending email", e);
        }
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