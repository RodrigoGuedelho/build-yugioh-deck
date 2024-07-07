package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.responseDtos.DeckResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface DeckService {
    public Deck save(Deck deck, String token);
    public Card addCard(Card card, Long id, String token) throws IOException, InterruptedException;
    public void removeCard(Long id, Long cardApiId, String token);
    public void remove(Long id, String token);
    public List<DeckResponse> find(String token);
    public Page<CardDTO> findCards(String token, Long deckId, int pageNumber, int pageSize) throws IOException;
}
