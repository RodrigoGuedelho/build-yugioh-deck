package com.guedelho.buildYourDeck.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.requestDtos.CardDTO;
import com.guedelho.buildYourDeck.responseDtos.CardDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardConvert {
    @Autowired
    private ModelMapper modelMapper;
    @Value("${api.images.directory.cards}")
    private String directoryImages;


    public CardDTO toObject(CardDto cardDto) throws IOException {
        CardDTO card = modelMapper.map(cardDto, CardDTO.class);
        card.setImage(FileUtil.getImage(directoryImages + cardDto.getImage()));
        card.setImageSmall(FileUtil.getImage(directoryImages + cardDto.getImageSmall()));
        card.setImageCropped(FileUtil.getImage(directoryImages + cardDto.getImageCropped()));
        return card;
    }

    public List<CardDTO> toCollectionDto(List<CardDto> cards) throws IOException {
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (CardDto cardDto: cards)
            cardDTOS.add(toObject(cardDto));
        return cardDTOS;
    }
    public List<CardDTO> toCollection(List<Card> cards) throws IOException {
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (Card card: cards)
            cardDTOS.add(toObject(card));
        return cardDTOS;
    }

    public CardDTO toObject(JsonNode card) {
        return new CardDTO(
                Long.parseLong(card.get("id").toString()),
                card.get("name").toString().replaceAll("\"", ""),
                card.get("type").toString().replaceAll("\"", ""),
                card.get("frameType").toString().replaceAll("\"", ""),
                Integer.parseInt(card.get("level").toString()),
                card.get("desc").toString().replaceAll("\"", ""),
                new BigDecimal(card.get("atk").toString()),
                new BigDecimal(card.get("def").toString()),
                card.get("race").toString().replaceAll("\"", ""),
                card.get("attribute").toString().replaceAll("\"", ""),
                card.get("archetype") != null? card.get("archetype").toString().replaceAll("\"", "") :"",
                card.get("card_images").get(0).get("image_url").toString().replaceAll("\"", ""),
                card.get("card_images").get(0).get("image_url_small").toString().replaceAll("\"", ""),
                card.get("card_images").get(0).get("image_url_cropped").toString().replaceAll("\"", "")
        );
    }

    public CardDTO toObject(Card card) throws IOException {
        CardDTO cardDTO = modelMapper.map(card, CardDTO.class);
        if (card.getImage() != null) {
            cardDTO.setImage(FileUtil.getImage(directoryImages + card.getImage().getImage()));
            cardDTO.setImageSmall(FileUtil.getImage(directoryImages + card.getImage().getImageSmall()));
            cardDTO.setImageCropped(FileUtil.getImage(directoryImages + card.getImage().getImageCropped()));
        }
        return cardDTO;
    }

    public List<CardDTO> toCollection(JsonNode cardsJson) {
        List<CardDTO> cards = new ArrayList<>();
        for (JsonNode card: cardsJson)
            cards.add(toObject(card));
        return cards;
    }
}
