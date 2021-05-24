package com.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//@Component
@Configuration
public class EmailCfg {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.password}")
    private String password;
/*
    public EmailCfg() {
        //username = "066de92fa1bdc0";
        //host = "smtp.mailtrap.io";
        //port = 2525;
        //password = "e03bc6ef02d807";
    }

    // @Bean -> https://blog.axopen.com/2019/02/java-spring-les-beans/
    @Bean
    public EmailCfg emailCfg() {
        return new EmailCfg();
    }
*/
}
