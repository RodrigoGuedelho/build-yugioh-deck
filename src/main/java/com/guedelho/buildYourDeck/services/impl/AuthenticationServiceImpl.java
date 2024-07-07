package com.guedelho.buildYourDeck.services.impl;

import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.requestDtos.AuthenticationDTO;
import com.guedelho.buildYourDeck.responseDtos.LoginResponseDTO;
import com.guedelho.buildYourDeck.security.TokenService;
import com.guedelho.buildYourDeck.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }
}
