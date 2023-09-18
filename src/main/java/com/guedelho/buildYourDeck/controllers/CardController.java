package com.guedelho.buildYourDeck.controllers;

import com.guedelho.buildYourDeck.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<Object> findCards(@RequestParam(value = "name", required = true) String name) throws IOException {
        return ResponseEntity.ok(cardService.findCards(name));
    }
}
