package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;

public interface UserService {
    public User save(UserRegisterDTO data);
    public User update(UserRegisterDTO data, String token);
    public User findByToken(String token);
}
