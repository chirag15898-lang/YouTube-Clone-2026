package com.youtube.central.Service;

import com.youtube.central.dto.NotificationMessage;
import com.youtube.central.model.AppUser;
import com.youtube.central.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService
{
    AppUserRepo appUserRepo;
    RabbitMqService rabbitMqService;

    @Autowired
    UserService(AppUserRepo appUserRepo,RabbitMqService rabbitMqService)
    {
        this.appUserRepo = appUserRepo;
        this.rabbitMqService = rabbitMqService;
    }

    public void  registerUser(AppUser user)
    {
        //Call repository layer to save the user
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        appUserRepo.save(user);

        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user-registration");
        message.setName(user.getName());
        rabbitMqService.insertMessageToQueue(message);

    }

}
