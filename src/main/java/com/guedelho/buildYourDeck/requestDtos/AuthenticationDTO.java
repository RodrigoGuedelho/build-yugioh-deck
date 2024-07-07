package com.guedelho.buildYourDeck.requestDtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank(message = "Login não informado.") String login,
                                @NotBlank(message = "Senha não informada.")String password) {
}
