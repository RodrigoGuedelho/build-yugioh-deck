package com.guedelho.buildYourDeck.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class ApiYugiohProDeckService {
    @Value("${api.yugioh.pro.deck}")
    private String url;
    private HttpService api;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiYugiohProDeckService.class);

    public JsonNode findCards(String name, String type, int level, String race, String attribute) {
        HttpResponse<String> response = null;
        String[] headers = {"Content-Type", "application/json"};
        ObjectMapper objectMapper = new ObjectMapper();
        api = new HttpService(url);
        try {
            String uri = "/api/v7/cardinfo.php?";
            if (!name.equals(""))
                   uri += "name=" +  name.replaceAll(" ", "%20");
            if (!type.equals(""))
                uri = addEIfNecessary(uri) + "type=" +  type.replaceAll(" ", "%20");
            if (level != 0)
                uri = addEIfNecessary(uri) + "level=" +  Integer.toString(level);
            if (!race.equals(""))
                uri = addEIfNecessary(uri) + "race=" +  race.replaceAll(" ", "%20");
            if (!attribute.equals(""))
                uri = addEIfNecessary(uri) + "attribute=" +  attribute.replaceAll(" ", "%20");

            response = api.get(uri, headers);
            if (response.statusCode() == HttpStatus.OK.value()) {
                JsonNode json = objectMapper.readTree(response.body());
                return json;
            }
        } catch (Exception ex) {
            LOGGER.error("Error: " + ex.getMessage(), ex);
        }
        return null;
    }

    private String addEIfNecessary(String text) {
        if (text.substring(text.length() -1, text.length()).equals("?"))
            return text;
        return text + "&&";
    }
}
