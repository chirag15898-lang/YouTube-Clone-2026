package com.youtube.notification_api.controller;

import com.youtube.notification_api.dto.NotificationMessage;
import com.youtube.notification_api.enums.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @RabbitListener(queues = "notification-queue")
    public void consumeMessage(@Payload NotificationMessage message)
    {
        if(message.getType().equals(NotificationType.user_registration.toString()))
        {

        }
        else if(message.getType().equals(NotificationType.channel_owner_subscriber_added.toString())){

        }
        else
        {

        }
    }
}
