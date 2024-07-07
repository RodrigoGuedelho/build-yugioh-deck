package com.guedelho.buildYourDeck.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface ApiYugiohProDeckService {
    public JsonNode findCards(String name, String type, int level, String race, String attribute);
}
