package com.guedelho.buildYourDeck.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "card")
@SequenceGenerator(name = "seq_card", sequenceName = "seq_card", allocationSize = 1, initialValue = 1)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_card")
    private Long id;
    private String name;
    private String type;
    @Column(name = "frame_type")
    private String frameType;
    private int level;
    private String description;
    private BigDecimal atk;
    private BigDecimal def;
    private String race;
    private String atribute;
    private String archetype;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
