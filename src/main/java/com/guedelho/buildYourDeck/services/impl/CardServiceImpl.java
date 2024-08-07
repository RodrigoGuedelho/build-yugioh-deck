package com.guedelho.buildYourDeck.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.repository.CardRepository;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.services.ApiYugiohProDeckService;
import com.guedelho.buildYourDeck.services.CardService;
import com.guedelho.buildYourDeck.utils.CardConvert;
import com.guedelho.buildYourDeck.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    private HttpServiceImpl httpService = new HttpServiceImpl("");
    @Autowired
    private ApiYugiohProDeckService apiYugiohProDeckService;
    @Value("${api.images.directory.cards}")
    private String directoryImages;
    @Autowired
    private CardConvert cardConvert;

    public Card save(Card card) throws IOException, InterruptedException {
        String[] headers = {"Content-Type", "application/octet-stream"};
        card = cardRepository.save(card);
        HttpResponse<byte[]> response = httpService.getBytes(card.getImage().getImage(), headers);

        String image = FileUtil.uploadImagem(card.getId().toString() + "_"
                + card.getName().replaceAll(" ", "_") + ".jpg", response.body(), directoryImages);
        card.getImage().setImage(image);

        response = httpService.getBytes(card.getImage().getImageSmall(), headers);

        String imageSmall = FileUtil.uploadImagem(card.getId().toString() + "_"
                + card.getName().replaceAll(" ", "_")
                + "_small.jpg", response.body(), directoryImages);
        card.getImage().setImageSmall(imageSmall);

        response = httpService.getBytes(card.getImage().getImageCropped(), headers);

        String imageCropped = FileUtil.uploadImagem(card.getId().toString() + "_"
                + card.getName().replaceAll(" ", "_")
                + "_cropped.jpg", response.body(), directoryImages);
        card.getImage().setImageCropped(imageSmall);
        return cardRepository.save(card);
    }

    public List<CardDTO> findCards(String cardName, String type, int level,
                                   String race, String attribute, boolean externalSearch) throws IOException {

        if (!externalSearch) {
            List<Card> cards = cardRepository.find(
                    "%" + cardName.toLowerCase() + "%",
                    "%" + type.toLowerCase() + "%",
                    level,
                    "%" + race.toLowerCase() + "%",
                    "%" + attribute.toLowerCase() + "%"
                    );
            List<CardDTO> cardDTOs = cardConvert.toCollection(cards);
            return cardDTOs;
        }

        JsonNode cardsAux = apiYugiohProDeckService.findCards(cardName.toLowerCase(),
                type.toLowerCase(), level, race.toLowerCase(),
                attribute.toLowerCase());
        List<CardDTO> cardDTOs = new ArrayList<>();
        if (cardsAux != null)
            cardDTOs = cardConvert.toCollection(cardsAux.get("data"));
        return cardDTOs;
    }

}
