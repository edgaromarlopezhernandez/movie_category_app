package com.movies.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "type_of_subscription")
    private String typeOfSubscription;

    @Column(name = "has_voted")
    private Boolean hasVoted;

    @Column(name = "movie_voted_id")
    private Long movieVotedId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(getIdClient(), client.getIdClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdClient());
    }

    @Override
    public String toString() {
        String movieVoted;
        if(this.movieVotedId == null) {
            movieVoted = "This user has not yet voted for any movie";
        } else {
            movieVoted = "Movie voted id: " + this.movieVotedId;
        }
        return "Client{" +
                "idClient=" + idClient +
                ", fullName='" + fullName + '\'' +
                ", typeOfSubscription='" + typeOfSubscription + '\'' +
                ", hasVoted=" + hasVoted +
                ", MovieVotedId=" + movieVoted +
                '}';
    }
}
