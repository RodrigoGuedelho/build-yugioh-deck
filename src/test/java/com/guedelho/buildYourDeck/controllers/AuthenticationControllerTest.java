package com.guedelho.buildYourDeck.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.utils.GenericTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest extends GenericTest {
    @Test
    @DisplayName("It Must return login successful when username and password is correct.")
    public void loginMustReturnSuccessful() throws Exception {
        this.mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"login\" : \"rodrigo\",\n" +
                        "\t\"password\": \"12345678\"\n" +
                        "}")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("It Must return forbidden when username or password is incorrect.")
    public void loginMustReturnForbidden() throws Exception {
        this.mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"login\" : \"rodrigo1\",\n" +
                        "\t\"password\": \"12345678\"\n" +
                        "}")).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It Must return Bad Request when username or password is not provided.")
    public void loginMustReturnBadRequest() throws Exception {
        this.mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"login\" : \"\",\n" +
                        "\t\"password\": \"\"\n" +
                        "}")).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("It should be saved when the attributes are correct.")
    public void saveMustReturnSuccessful() throws Exception {
        UserRegisterDTO request =  new UserRegisterDTO("roberio",
                "Roberio Guedelho", "12345678");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("It should not be saved when user already exist.")
    public void saveUserAlreadyExist() throws Exception {
        UserRegisterDTO request =  new UserRegisterDTO("rodrigo",
                "Rodrigo Guedelho", "12345678");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("It should return bad request when attributes not provided.")
    public void saveBadRequest() throws Exception {
        UserRegisterDTO request =  new UserRegisterDTO("",
                "", "");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isBadRequest());
    }
}
