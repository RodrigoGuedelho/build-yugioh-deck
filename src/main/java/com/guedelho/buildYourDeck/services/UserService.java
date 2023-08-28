package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.exceptions.BadRequestException;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.UserRepository;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.security.TokenService;
import com.guedelho.buildYourDeck.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public User save(UserRegisterDTO data) {
        if(this.userRepository.findByLogin(data.login()) != null)
            throw new BadRequestException("The user with that login name already exists.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), data.name(), encryptedPassword, data.role());

        return this.userRepository.save(newUser);
    }

    public User findByToken(String token){
        var login = tokenService.validateToken(TokenUtil.removeBearerName(token));
        var user = userRepository.findByLogin(login);
        return user;
    }
}
