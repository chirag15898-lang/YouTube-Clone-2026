package com.youtube.central.controller;

import com.youtube.central.Service.UserService;
import com.youtube.central.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/central/user")

public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
      this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser user)
    {
         userService.registerUser(user);
    }
}
