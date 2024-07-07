package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;

import java.io.IOException;
import java.util.List;

public interface CardService {
    public Card save(Card card) throws IOException, InterruptedException;
    public List<CardDTO> findCards(String cardName, String type, int level,
                                   String race, String attribute, boolean externalSearch) throws IOException;
}
