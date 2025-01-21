package com.example.simplecrudapi;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
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
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password")  String password, HttpServletResponse response) throws Exception {
        AppUser appUser =userService.userLogin(username,password);
        if (!passwordEncoder.matches(password, appUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        String jwtToken = jwtUtil.generateToken(appUser.getUsername());
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60); // 1 godzina
        response.addCookie(cookie);
        ResponseToken responseToken=new ResponseToken();
        responseToken.setToken(jwtToken);
        responseToken.setExpirationDate(jwtUtil.getExpiration(jwtToken));
        return ResponseEntity.ok(responseToken);
    }


}
