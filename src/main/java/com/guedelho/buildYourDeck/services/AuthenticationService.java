package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.requestDtos.AuthenticationDTO;
import com.guedelho.buildYourDeck.responseDtos.LoginResponseDTO;

public interface AuthenticationService {
    public LoginResponseDTO login(AuthenticationDTO data);
}
