package com.guedelho.buildYourDeck.repository;

import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
import com.guedelho.buildYourDeck.responseDtos.CardDto;
import com.guedelho.buildYourDeck.responseDtos.DeckResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    @Query("select new com.guedelho.buildYourDeck.responseDtos.DeckResponse(d.id, d.description) d from Deck d " +
            "where d.user.id = :userId")
    public List<DeckResponse> find(@Param("userId") Long userId);

    /*@Query("select d.cards from Deck d " +
            "where d.id = :id and d.user.id = :userId")*/

    @Query(value = "select c.id,c.archetype,c.atk,c.attribute,c.card_api_id as cardApiId, " +
            "c.def,c.description,c.frame_type as frameType, " +
            "c.image_id as imageId,c.level,c.name,c.race,c.type, " +
            "i.image, i.image_small as imageSmall, i.image_cropped as imageCropped " +
            "from deck d " +
            "            join deck_cards dc on (dc.deck_id = d.id) " +
            "            join card c on (c.id = dc.cards_id) " +
            "            join image i on (i.id = c.image_id) " +
            "where d.id = :id and d.user_id = :userId " +
            "offset :pageNumber rows fetch first :pageSize rows only", nativeQuery = true)
    public List<CardDto> findCardsDeck(@Param("userId") Long userId, @Param("id") Long id,
                                       @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);

}
