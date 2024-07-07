package com.guedelho.buildYourDeck.controllers;

import com.guedelho.buildYourDeck.utils.GenericTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.net.URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CardControllerTest extends GenericTest {

    @Test
    @DisplayName("it should return a list of cards successfully when no parameters are provided")
    public void findCardsInternalResearchBase() throws Exception {
        URI uri = URI.create("/v1/decks/11/cards?pageSize=5&&pageNumber=0");
        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getTokenApi())
        ).andExpect(status().is2xxSuccessful());
    }
}
