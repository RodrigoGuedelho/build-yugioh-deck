package com.guedelho.buildYourDeck.repository;

import com.guedelho.buildYourDeck.models.Card;
import com.guedelho.buildYourDeck.models.Deck;
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
    public List<Deck> find(@Param("userId") Long userId);

    @Query("select c from Deck d " +
            "join d.cards c " +
            "where d.id = :id and d.user.id = :userId")
    public Page<Card> findCardsDeck(@Param("userId") Long userId, @Param("id") Long id, Pageable pageable);
}
