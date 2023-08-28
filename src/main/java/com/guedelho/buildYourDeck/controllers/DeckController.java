package com.guedelho.buildYourDeck.controllers;

import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.requestDtos.DeckDTO;
import com.guedelho.buildYourDeck.responseDtos.DeckResponse;
import com.guedelho.buildYourDeck.services.DeckService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/decks")
public class DeckController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DeckService deckService;

    @GetMapping
    public ResponseEntity<Object> find(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(deckService.find(token));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid DeckDTO dada, @RequestHeader("Authorization") String token) {
        var deck = deckService.save(toEntity(dada), token);
        return ResponseEntity.ok(new DeckResponse(deck.getId(), deck.getDescription()));
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<Object> findCards(@RequestHeader("Authorization") String token, @PathVariable("id") Long id,
                                            @RequestParam(value="pageNumber", required = false, defaultValue="0") int pageNumber,
                                            @RequestParam(value="pageSize", required = false, defaultValue="20") int pageSize) throws IOException {
        return ResponseEntity.ok(deckService.findCards(token, id, pageNumber, pageSize));
    }
    @PutMapping("/{id}/cards")
    public ResponseEntity<Object> addCard(@RequestHeader("Authorization") String token, @PathVariable("id") Long id,
                                          @RequestBody CardDTO cardDto) throws IOException, InterruptedException {
        return ResponseEntity.ok(deckService.addCard(toEntity(cardDto), id, token));
    }

    @DeleteMapping("/{id}/cards")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCard(@RequestHeader("Authorization") String token, @PathVariable("id") Long id, @RequestBody CardDTO cardDto) {
        deckService.removeCard(toEntity(cardDto), id, token);
    }

    private Card toEntity(CardDTO data) {
        Card card = modelMapper.map(data, Card.class);
        return card;
    }
    private Deck toEntity(DeckDTO data) {
        Deck deck = modelMapper.map(data, Deck.class);
        return deck;
    }
}
