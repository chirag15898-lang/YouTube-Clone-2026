package com.youtube.notification_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

@Configuration
public class config {
    @Bean
    public TemplateEngine getTemplateEngine()
    {
        return new TemplateEngine();
    }
    @Bean
    public JavaMailSender javaMailSender()
    {
        return new JavaMailSenderImpl();
    }
}
