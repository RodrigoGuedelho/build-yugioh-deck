package com.guedelho.buildYourDeck.requestDtos;

import com.guedelho.buildYourDeck.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(@NotBlank(message = "Login não informado.") String login,
                              @NotBlank(message = "Nome não informado") String name,
                              @NotBlank(message = "Senha não informada") String password) {
}
