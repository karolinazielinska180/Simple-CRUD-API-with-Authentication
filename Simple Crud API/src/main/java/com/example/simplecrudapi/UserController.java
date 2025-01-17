package com.example.simplecrudapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
    @GetMapping("/login")
    public String viewLoginForm(Model model) {
        AppUserDto appUserDto=new AppUserDto();
        model.addAttribute("appUser", appUserDto);
        return "login_form";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,@RequestParam("password")  String password) throws Exception {
        AppUser appUser =userService.findByUsername(username);

        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        String jwtToken = jwtUtil.generateToken(appUser.getUsername());
        return ResponseEntity.ok(Map.of("token", jwtToken));
    }


}
