package com.guedelho.buildYourDeck.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/decks")
public class DeckController {

    @GetMapping
    public ResponseEntity<Object> find() {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
