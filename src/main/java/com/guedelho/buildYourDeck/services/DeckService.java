package com.guedelho.buildYourDeck.services;

import com.guedelho.buildYourDeck.exceptions.BadRequestException;
import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.CardRepository;
import com.guedelho.buildYourDeck.repository.DeckRepository;
import com.guedelho.buildYourDeck.repository.UserRepository;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.responseDtos.CardDto;
import com.guedelho.buildYourDeck.responseDtos.DeckResponse;
import com.guedelho.buildYourDeck.security.TokenService;
import com.guedelho.buildYourDeck.utils.CardConvert;
import com.guedelho.buildYourDeck.utils.FileUtil;
import com.guedelho.buildYourDeck.utils.TokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardConvert cardConvert;
    @Autowired
    private ModelMapper modelMapper;

    public Deck save(Deck deck, String token){
        var user = userService.findByToken(token);
        deck.setUser(user);
        return deckRepository.save(deck);
    }

    public Card addCard(Card card, Long id, String token) throws IOException, InterruptedException {
        Optional<Deck> deckOptional = deckRepository.findById(id);
        User user = userService.findByToken(token);
        Optional<Card> cardOptional = cardRepository.findByCardApiId(card.getCardApiId());
        BadRequestException exception = validate(deckOptional, user);

        if (exception != null)
            throw exception;

        if (cardOptional.isEmpty())
            card = cardService.save(card);
        else
            card = cardOptional.get();

        Deck deck = deckOptional.get();
        deck.getCards().add(card);

        deckRepository.save(deck);
        return deck.getCards().get(deck.getCards().size()-1);
    }

    public void removeCard(Long id, Long cardApiId, String token) {
        Optional<Deck> deckOptional = deckRepository.findById(id);
        User user = userService.findByToken(token);
        BadRequestException exception = validate(deckOptional, user);
        Optional<Card> card = cardRepository.findByCardApiId(cardApiId);
        if (exception != null)
            throw exception;
        if (card.isEmpty())
            throw new BadRequestException("Card not exist.");

        Deck deck = deckOptional.get();
        deck.getCards().remove(card.get());
        deckRepository.save(deck);
    }

    public List<DeckResponse> find(String token) {
        var user = userService.findByToken(token);
        return deckRepository.find(user.getId());
    }

    public Page<CardDTO> findCards(String token, Long deckId, int pageNumber, int pageSize) throws IOException {
        var user = userService.findByToken(token);
        List<CardDTO> cards = cardConvert.toCollectionDto(
                deckRepository.findCardsDeck(user.getId(), deckId, pageNumber, pageSize));

        Page<CardDTO> page = new PageImpl<>(cards);
        return page;
    }

    private BadRequestException validate(Optional<Deck> deckOptional, User user) {
        if (deckOptional.isEmpty())
            return new BadRequestException("Deck not found.");
        if (!deckOptional.get().getUser().equals(user))
            return new BadRequestException("User does not own this deck.");
        return null;
    }
}
