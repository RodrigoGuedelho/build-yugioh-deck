package com.guedelho.buildYourDeck.controllers;

import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uptade(@RequestBody @Valid UserRegisterDTO data, @RequestHeader("Authorization") String token){
        var user = userService.update(data, token);
    }
}
