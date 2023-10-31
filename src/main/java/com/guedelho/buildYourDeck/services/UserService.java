package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.enums.UserRole;
import com.guedelho.buildYourDeck.exceptions.BadRequestException;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.UserRepository;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.security.TokenService;
import com.guedelho.buildYourDeck.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    public User save(UserRegisterDTO data) {
        if(this.userRepository.findByLogin(data.login()) != null)
            throw new BadRequestException("Usuário com esse nome de login já existe.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), data.name(), encryptedPassword, UserRole.USER);

        return this.userRepository.save(newUser);
    }

    public User update(UserRegisterDTO data, String token) {
        User user = findByToken(token);
        User userComparation = userRepository.findByLogin(data.login());

        if(userComparation != null && userComparation.getLogin().equals(data.login()) && !user.getId().equals(userComparation.getId()))
            throw new BadRequestException("Usuário com esse nome de login já existe.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User updateUser = new User(data.login(), data.name(), encryptedPassword, UserRole.USER);
        updateUser.setId(user.getId());
        return this.userRepository.save(updateUser);
    }

    public User findByToken(String token){
        var login = tokenService.validateToken(TokenUtil.removeBearerName(token));
        var user = userRepository.findByLogin(login);
        return user;
    }
}
