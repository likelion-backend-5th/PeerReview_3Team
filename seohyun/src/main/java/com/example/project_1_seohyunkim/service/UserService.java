package com.example.project_1_seohyunkim.service;


import com.example.project_1_seohyunkim.jwt.JwtTokenUtils;
import com.example.project_1_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsManager customUserDetailsManager;

    public void createUser(UserDetails user) {
        customUserDetailsManager.createUser(user);
    }

    public String login(String username, String password){
        UserDetails user = customUserDetailsManager.loadUserByUsername(username);

        if(!customUserDetailsManager.userExists(username)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        if(!passwordEncoder.matches(password, user.getPassword()))  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");

        String token = jwtTokenUtils.generateToken(user);
        return token;
    }

    public UserDetails profile(String userId){
        return customUserDetailsManager.loadUserByUsername(userId);
    }
}
