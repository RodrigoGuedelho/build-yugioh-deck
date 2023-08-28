package com.guedelho.buildYourDeck.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "deck")
@SequenceGenerator(name = "seq_deck", sequenceName = "seq_deck", allocationSize = 1, initialValue =1)
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_deck")
    private Long id;
    private String description;
    @OneToMany
    private List<Card> cards;
    @OneToOne()
    @JoinColumn(unique = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        return id.equals(deck.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
