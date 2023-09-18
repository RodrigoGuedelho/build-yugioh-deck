package com.guedelho.buildYourDeck.requestDtos;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    @NotNull
    private Long cardApiId;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private String frameType;
    @NotNull
    private int level;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal atk;
    @NotNull
    private BigDecimal def;
    @NotNull
    private String race;
    @NotNull
    private String attribute;
    @NotNull
    private String archetype;
    @NotNull
    private String image;
    @NotNull
    private String imageSmall;
    @NotNull
    private String imageCropped;
}
