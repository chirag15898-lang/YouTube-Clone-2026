package com.youtube.central.Service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youtube.central.dto.NotificationMessage;

@Service
public class RabbitMqService {

    @Autowired
   RabbitTemplate rabbitTemplate;

    public final String exchangeName = "notification-exchange";
    public final String queueName = "notification-queue";
    public final String routingKey = "notification-123";

    public void insertMessageToQueue(NotificationMessage message)
    {
     rabbitTemplate.convertAndSend(routingKey , exchangeName , message);
    }
}
 