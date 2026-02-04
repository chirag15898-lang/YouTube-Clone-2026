package com.youtube.central.service;

import com.youtube.central.dto.NotificationMessage;
import com.youtube.central.dto.UserCredentialsDTO;
import com.youtube.central.models.AppUser;
import com.youtube.central.repository.AppUserRepo;
import com.youtube.central.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class UserService {
    AppUserRepo appUserRepo;


    RabbitMqService rabbitMqService;
    @Autowired
    public UserService(AppUserRepo appUserRepo,
                       RabbitMqService rabbitMqService){
        this.appUserRepo = appUserRepo;
        this.rabbitMqService = rabbitMqService;
    }

    public AppUser getUserByEmail(String email){
        return appUserRepo.findByEmail(email);
    }

    public String loginUser(UserCredentialsDTO credentials){
        String email = credentials.getEmail(); // H.W. validate email
        AppUser user = this.getUserByEmail(email);
        if(user.getPassword().equals(credentials.getPassword())){
            // generate token
            String cred = user.getEmail() + ":" + user.getPassword();
            return cred;
        }
        return "Incorrect Password";
    }

    public void registerUser(AppUser user){
        // Call repository layer to save the user
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        appUserRepo.save(user);
        // Insert user registration message payload inside rabbit mq queue.
        NotificationMessage message = new NotificationMessage();
        message.setEmail(user.getEmail());
        message.setType("user_registration");
        message.setName(user.getName());
        rabbitMqService.insertMessageToQueue(message);
    }



    public AppUser getUserById(UUID userId){
        return appUserRepo.findById(userId).orElse(null);
    }

}