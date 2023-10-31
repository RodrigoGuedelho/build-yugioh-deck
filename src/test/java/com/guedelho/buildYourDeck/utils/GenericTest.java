package com.guedelho.buildYourDeck.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guedelho.buildYourDeck.enums.UserRole;
import com.guedelho.buildYourDeck.models.User;
import com.guedelho.buildYourDeck.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class GenericTest {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public MockMvc mockMvc;
    private String tokenApi;

    @BeforeEach
    public void init() throws Exception {
        userRepository.deleteAll();
        String encryptedPassword = new BCryptPasswordEncoder().encode("12345678");
        User user = new User("rodrigo", "Rodrigo Guedelho",
                encryptedPassword, UserRole.ADMIN);
        user = userRepository.save(user);
        authenticationApi();
    }

    private void authenticationApi() throws Exception {
        var response = this.mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"login\" : \"rodrigo\",\n" +
                        "\t\"password\": \"12345678\"\n" +
                        "}"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson =objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
        tokenApi = responseJson.get("token").toString();
        tokenApi = tokenApi.replaceAll("\"", "");
    }

    public String getTokenApi() {
        return tokenApi;
    }
}
