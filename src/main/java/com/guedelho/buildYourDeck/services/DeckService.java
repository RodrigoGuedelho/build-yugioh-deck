package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;
}
