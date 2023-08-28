package com.guedelho.buildYourDeck.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseException {
    public int statusCode;
    public String status;
    public String message;
}
