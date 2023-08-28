package com.guedelho.buildYourDeck.requestDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeckDTO {
    @NotBlank
    private String description;
    private List<CardDTO> cards;
}
