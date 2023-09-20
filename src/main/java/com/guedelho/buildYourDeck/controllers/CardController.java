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
    public ResponseEntity<Object> findCards(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                            @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                            @RequestParam(value = "level", required = false, defaultValue = "0") int level,
                                            @RequestParam(value = "race", required = false, defaultValue = "") String race,
                                            @RequestParam(value = "attribute", required = false, defaultValue = "") String attribute,
                                            @RequestParam(value = "externalSearch", required = false, defaultValue = "false") boolean externalSearch) throws IOException {
        return ResponseEntity.ok(cardService.findCards(name, type, level, race, attribute, externalSearch));
    }
}
