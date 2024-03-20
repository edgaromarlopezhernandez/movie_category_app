package com.movies.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private Long idMovie;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "release_year")
    private LocalDate releaseYear;

    @Column(name = "genres")
    private String genres;

    @Column(name = "votes")
    private Long votes;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_image_movie")
    private ImageMovie image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(getIdMovie(), movie.getIdMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdMovie());
    }

    @Override
    public String toString() {
        String theImage;
        if(this.image != null){
            theImage = "Theres an image with id: " + this.image.getIdImageMovie();
        } else {
            theImage = "There's no image yet";
        }
        return "Movie{" +
                "idMovie=" + idMovie +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", genres='" + genres + '\'' +
                ", votes=" + votes +
                ", image=" + theImage +
                '}';
    }
}
