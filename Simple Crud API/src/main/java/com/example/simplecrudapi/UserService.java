package com.example.simplecrudapi;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AppUser registerNewUser(AppUserDto appUserDto) throws Exception {
        if (userRepository.findByUsername(appUserDto.getUsername()).isPresent()) {
            throw new Exception("User with that username already exists!");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDto.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        userRepository.save(appUser);
        return appUser;
    }

    public AppUser findByUsername(String username) throws Exception {
        AppUser appUser = userRepository.findByUsername(username).orElse(null);
        return appUser;

    }
    public AppUser userLogin(String username,String password){
        AppUserDto appUserDto=new AppUserDto();
        appUserDto.setUsername(username);
        appUserDto.setPassword(passwordEncoder.encode(password));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        appUserDto.getUsername(),
                        appUserDto.getPassword()
                )
        );
        return userRepository.findByUsername(appUserDto.getUsername()).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
