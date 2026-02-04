package com.youtube.central.service;

import com.youtube.central.dto.CreateChannelRequestBody;
import com.youtube.central.dto.NotificationMessage;
import com.youtube.central.exceptions.ChannelNotFound;
import com.youtube.central.exceptions.UserNotFound;
import com.youtube.central.models.AppUser;
import com.youtube.central.models.Channel;
import com.youtube.central.repository.AppUserRepo;
import com.youtube.central.repository.ChannelRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChannelService {

    @Autowired
    UserService userService;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    ChannelRepo channelRepo;

    public Channel getChannelById(UUID channelId){
        return channelRepo.findById(channelId).orElse(null);
    }

    public void updateChannel(Channel channel){
        channelRepo.save(channel);
    }

    public void createChannel(CreateChannelRequestBody channelDetails){
        String email = channelDetails.getUserEmail();
        // we need to check with this email user is present inside database or not
        AppUser user = userService.getUserByEmail(email);
        if(user == null){
            // User does not exist it is wrong email
            throw new UserNotFound(
                    String.format("User with email id %s does not exist in system", email)
            );
        }
        // We need to create channel for that user.
        Channel channel = new Channel();
        channel.setCreatedAt(LocalDateTime.now());
        channel.setUpdatedAt(LocalDateTime.now());
        channel.setMonetized(false);
        channel.setUser(user);
        channel.setDescription(channel.getDescription());
        channel.setName(channelDetails.getChannelName());

        // call my repo layer which will save channel inside channel table
        channelRepo.save(channel);

        // Notify user that hey we have created channel for you in your system
        // We need to mail user
        // So to mail user we need to upload notification message at our message queue

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setName(user.getName());
        notificationMessage.setType("create_channel");
        notificationMessage.setEmail(user.getEmail());
        rabbitMqService.insertMessageToQueue(notificationMessage);
    }


    public void addSubscriber(UUID userId, UUID channelId){

        // I need to validate both userId and channelId

        AppUser user = userService.getUserById(userId);
        // We are checking userId is present in our system or not
        if(user == null){
            throw new UserNotFound(String.format("" +
                    "User with id %s does not exist in the system.", userId.toString()));
        }
        // We need to check channelId is present in our system or not

        Channel channel = this.getChannelById(channelId);
        if(channel == null){
            // That means channel does not exist in system
            throw new ChannelNotFound(String.format("Channel with channelId %s does not exist in system"));
        }
        channel.setTotalSubs(channel.getTotalSubs() + 1);
        List<AppUser> subscribers = channel.getSubscribers();
        subscribers.add(user);

        // {id : 1, descriptin: "Hello", subscribers: [{id : 1}, {id : 3}]}

        // I have updated list of subscribers for a particular channel object.
        // Whatever i have updated i need to save this changes in the database.
        channelRepo.save(channel);

        // channelowner should get mail hey new susbcriber added in your channel


        // Notification Message -> I will pass this notification message to the messaging queue

        NotificationMessage message = new NotificationMessage();
        message.setEmail(channel.getUser().getEmail());
        message.setType("subscriber_added");
        message.setName(channel.getName());

        rabbitMqService.insertMessageToQueue(message);
    }
}