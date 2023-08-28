package com.guedelho.buildYourDeck.utils;

public class TokenUtil {

    public static String removeBearerName(String token) {
        return token.replace("Bearer ", "");
    }
}
