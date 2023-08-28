package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.exceptions.BadRequestException;
import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.DeckRepository;
import com.guedelho.buildYourDeck.repository.UserRepository;
import com.guedelho.buildYourDeck.responseDtos.DeckResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeckServiceTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeckService deckService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   @Test
    public void testAddCardToDeck() {
       // Mock data
       Long deckId = 1L;
       String token = "testToken";
       User user = new User();
       user.setId(1L);
       Card card = new Card();

       Deck deck = new Deck();
       deck.setId(deckId);
       deck.setUser(user);
       deck.setCards(new ArrayList<>()); // Initialize the cards list

       when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
       when(userService.findByToken(token)).thenReturn(user);
       when(deckRepository.save(deck)).thenReturn(deck);

       // Call the service method
       Card addedCard = deckService.addCard(card, deckId, token);

       // Assertions
       verify(deckRepository, times(1)).findById(deckId);
       verify(userService, times(1)).findByToken(token);
       verify(deckRepository, times(1)).save(deck);

       assertEquals(card, addedCard);
    }

    @Test
    public void testRemoveCardFromDeck() {
        // Mock data
        Long deckId = 1L;
        String token = "testToken";
        User user = new User();
        user.setId(1L);
        Card card = new Card();

        Deck deck = new Deck();
        deck.setId(deckId);
        deck.setUser(user);
        deck.setCards(new ArrayList<>()); // Initialize the cards list
        deck.getCards().add(card);

        when(deckRepository.findById(deckId)).thenReturn(Optional.of(deck));
        when(userService.findByToken(token)).thenReturn(user);
        when(deckRepository.save(deck)).thenReturn(deck);

        // Call the service method
        deckService.removeCard(card, deckId, token);

        // Assertions
        verify(deckRepository, times(1)).findById(deckId);
        verify(userService, times(1)).findByToken(token);
        verify(deckRepository, times(1)).save(deck);

        assertEquals(0, deck.getCards().size());
    }

    @Test
    public void testFindDecks() {
        // Mock data
        String token = "testToken";
        User user = new User();
        user.setId(1L);
        List<DeckResponse> deckResponses = new ArrayList<>();

        when(userService.findByToken(token)).thenReturn(user);
        when(deckRepository.find(user.getId())).thenReturn(deckResponses);

        // Call the service method
        List<DeckResponse> foundDecks = deckService.find(token);

        // Assertions
        verify(userService, times(1)).findByToken(token);
        verify(deckRepository, times(1)).find(user.getId());

        assertEquals(deckResponses, foundDecks);
    }

    @Test
    public void testFindCardsInDeck() {
        // Mock data
        String token = "testToken";
        Long deckId = 1L;
        User user = new User();
        user.setId(1L);
        Pageable pageable = Pageable.unpaged();
        List<Card> cards = new ArrayList<>();

        when(userService.findByToken(token)).thenReturn(user);
        when(deckRepository.findCardsDeck(user.getId(), deckId, pageable)).thenReturn(new PageImpl<>(cards));

        // Call the service method
        Page<Card> foundCards = deckService.findCards(token, deckId, pageable);

        // Assertions
        verify(userService, times(1)).findByToken(token);
        verify(deckRepository, times(1)).findCardsDeck(user.getId(), deckId, pageable);

        assertEquals(cards, foundCards.getContent());
    }

    // Similar test methods can be created to cover the remaining methods of DeckService

}
