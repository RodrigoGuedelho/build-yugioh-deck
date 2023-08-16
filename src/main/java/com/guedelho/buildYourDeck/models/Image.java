package com.guedelho.buildYourDeck.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image")
@SequenceGenerator(name = "seq_image", sequenceName = "seq_image", initialValue = 1, allocationSize = 1)
public class Image {
    @Id
    @GeneratedValue(generator = "seq_image", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String image;
    @Column(name = "image_small")
    private String imageSmall;
    @Column(name = "image_cropped")
    private String imageCropped;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return id.equals(image.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
