package com.guedelho.buildYourDeck.controllers;

import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.UserRepository;
import com.guedelho.buildYourDeck.requestDtos.AuthenticationDTO;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.services.AuthenticationService;

import com.guedelho.buildYourDeck.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(authenticationService.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterDTO data){
        var user = userService.save(data);
        return ResponseEntity.ok(user);
    }
}
