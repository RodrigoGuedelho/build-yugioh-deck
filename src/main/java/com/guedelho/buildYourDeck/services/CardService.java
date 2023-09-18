package com.guedelho.buildYourDeck.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.repository.CardRepository;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.utils.CardConvert;
import com.guedelho.buildYourDeck.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    private HttpService httpService = new HttpService("");
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

    public List<CardDTO> findCards(String cardName) throws IOException {
        List<Card> cards = cardRepository.findByName(cardName.toLowerCase());
        JsonNode cardsAux = null;
         List<CardDTO> cardDTOs = cardConvert.toCollection(cards);
        if (cards.isEmpty()) {
            cardsAux = apiYugiohProDeckService.findCards(cardName);
            cardDTOs = cardConvert.toCollection(cardsAux.get("data"));
        }
        return cardDTOs;
    }

}
