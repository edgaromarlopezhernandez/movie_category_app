package com.movies.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "image_movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image_movie")
    private Long idImageMovie;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @OneToOne(mappedBy = "image")
    private Movie movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMovie that)) return false;
        return Objects.equals(getIdImageMovie(), that.getIdImageMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdImageMovie());
    }

    @Override
    public String toString() {
        String theImage;
        if(this.image != null){
            theImage = "Theres an image here";
        } else {
            theImage = "There's no image yet";
        }
        return "ImageMovie{" +
                "idImageMovie=" + idImageMovie +
                ", image=" + theImage +
                ", movie=" + movie +
                '}';
    }
}
