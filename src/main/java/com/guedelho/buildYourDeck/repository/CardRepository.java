package com.guedelho.buildYourDeck.repository;

import com.guedelho.buildYourDeck.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    public Optional<Card> findByCardApiId(@Param("cardApiId") Long cardApiId);
}
