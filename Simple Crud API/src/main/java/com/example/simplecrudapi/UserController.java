package com.example.simplecrudapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public String register(@RequestBody AppUserDto appUserDto) {
        return "/register";
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUserDto appUserDto) {
        return "/login";
    }


}
