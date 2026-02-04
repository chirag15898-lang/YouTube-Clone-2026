package com.youtube.central.controller;

import com.youtube.central.dto.UserCredentialsDTO;
import com.youtube.central.models.AppUser;
import com.youtube.central.security.JwtUtil;
import com.youtube.central.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/central/user")
public class UserController {
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody AppUser user){

        userService.registerUser(user);
        String credentials = user.getEmail() + ":" + user.getPassword();
        return jwtUtil.generateToken(credentials);
    }

    @GetMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserCredentialsDTO credentials){
        String resp = userService.loginUser(credentials);
        if(resp.equals("Incorrect Password")){
            return new ResponseEntity("Incorrect Password", HttpStatus.UNAUTHORIZED);
        }else{
            String token = jwtUtil.generateToken(resp);
            return new ResponseEntity(token, HttpStatus.OK);
        }
    }
}