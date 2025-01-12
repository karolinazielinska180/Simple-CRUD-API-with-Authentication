package com.example.simplecrudapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String viewRegisterForm(Model model) {
        AppUserDto appUserDto=new AppUserDto();
        model.addAttribute("appUser", appUserDto);
        return "register_form";
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@ModelAttribute("appUser") AppUserDto appUserDto) throws Exception {
        AppUser registered=userService.registerNewUser(appUserDto);
        return new ModelAndView("register_form","appUser", registered);
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUserDto appUserDto) {
        return "/login";
    }


}
