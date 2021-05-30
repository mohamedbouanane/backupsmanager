package com.api.config;

import java.io.Console;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// @Component
@RestController
// @Configuration
public class EmailCfg {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Value("${spring.mail.smtp-auth}")
    private String smtpAuth;

    @Value("${spring.mail.smtp-port}")
    private String smtpPort;

    @Value("${spring.mail.smtp-host}")
    private String smtpHost;

    @Value("${spring.mail.smtp-starttls-enable}")
    private String smtpStarttlsEnable;

    @Value("${spring.mail.smtp-starttls-required}")
    private String smtpStarttlsRequired;

    @Value("${spring.mail.ssl-enable}")
    private String sslEnable;

    // @Bean -> https://blog.axopen.com/2019/02/java-spring-les-beans/
    // @Bean
    public JavaMailSenderImpl javaMailSender0() {

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding(defaultEncoding);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);

        return mailSender;
    }

    public JavaMailSenderImpl javaMailSender1() throws MailException {

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        System.out.println(username + " eeeeeeeeeeeeeeeeeeeeeeeeeeee");
        mailSender.setPassword(password);
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.host", "smtp.mailtrap.io");

        return mailSender;
    }

    public JavaMailSenderImpl javaMailSender() throws MailException {

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("066de92fa1bdc0");
        mailSender.setPassword("e03bc6ef02d807");
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.host", "smtp.mailtrap.io");

        return mailSender;
    }

}
