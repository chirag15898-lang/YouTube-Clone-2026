package com.youtube.notification_api.factory;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailFactory {

    @Autowired
    JavaMailSender javaMailSender;

    public MimeMessage createMimeMailMessage() {
      return javaMailSender.createMimeMessage();
    }

    public MimeMessageHelper createMimeMailMessageHelper(MimeMessage mimeMessage)
    {

    }

}
