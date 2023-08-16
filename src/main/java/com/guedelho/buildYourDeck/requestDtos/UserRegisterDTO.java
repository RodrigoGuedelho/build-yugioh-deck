package com.guedelho.buildYourDeck.requestDtos;

import com.guedelho.buildYourDeck.enums.UserRole;

public record UserRegisterDTO(String login, String name, String password, UserRole role) {
}
