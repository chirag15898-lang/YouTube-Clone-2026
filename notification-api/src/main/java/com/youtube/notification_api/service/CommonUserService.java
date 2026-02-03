package com.youtube.notification_api.service;

import com.youtube.notification_api.dto.NotificationMessage;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class CommonUserService
  {
      @Autowired
      TemplateEngine templateEngine;

      @Autowired
      JavaMailSender javaMailSender;

      @Value("${youtube.platform.name}")
      String platformName

    public void senduserRegistrationEmail(NotificationMessage notificationMessage)
    {
        // this function will send registration email to the user
        // So,email is of type html so we need to get html template
        // Before getting html template we need to create variable inside html template
        Context context = new Context();
        context.setVariable("userName",notificationMessage.getName());
        context.setVariable("platformName","Youtube");

        //We need to get our HTML template in form of string and all the variable populated inside html template

        String htmlEmailContent = templateEngine.process("user-registration-email",context);

        // templateengine.process - will insert values for all the variables defined html template

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // I need to set this html content inside mimemessage

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setTo(notificationMessage.getEmail());
        mimeMessageHelper.setSubject("Welcome to YouTube");

        mimeMessageHelper.setText(htmlEmailContent,true);

    }
  }
