package com.guedelho.buildYourDeck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.guedelho.buildYourDeck.enums.UserRole;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.requestDtos.UserRegisterDTO;
import com.guedelho.buildYourDeck.utils.GenericTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends GenericTest {
    @Test
    @DisplayName("It Must save when the body is correct.")
    public void updateMustSave() throws Exception {
        UserRegisterDTO request =  new UserRegisterDTO("rodrigo",
                "Rodrigo Guedelho", "12345678");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getTokenApi())
                .content(requestJson)
        ).andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("It Must return forbidden when the token is invalide.")
    public void updateMustForbidden() throws Exception {
        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer sdsdsdsdsd.sdsdsds.sdsdsds")
                .content("")
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It Must return bad request when the user with usernmame ready exist.")
    public void updateMustReturnUserExist() throws Exception {
        userRepository.save(new User("roberio", "Roberio Guedelho", "", UserRole.ADMIN));
        UserRegisterDTO request =  new UserRegisterDTO("roberio",
                "Rodrigo Guedelho", "12345678");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getTokenApi())
                .content(requestJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("It Must return bad request when an attribute is not provided.")
    public void updateMustReturnBadRequest() throws Exception {
        UserRegisterDTO request =  new UserRegisterDTO("",
                "", "");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getTokenApi())
                .content(requestJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("It Must return forbidden when the username is updated and token is not updated.")
    public void updateMustReturnUserNotExist() throws Exception {
        User user = userRepository.findByLogin("rodrigo");
        user.setLogin("roberio");
        userRepository.save(user);

        UserRegisterDTO request =  new UserRegisterDTO("roberio",
                "Rodrigo Guedelho", "12345678");
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getTokenApi())
                .content(requestJson)
        ).andExpect(status().isForbidden());
    }
}
