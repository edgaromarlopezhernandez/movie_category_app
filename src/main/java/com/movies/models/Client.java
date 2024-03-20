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
        return "Client{" +
                "idClient=" + idClient +
                ", fullName='" + fullName + '\'' +
                ", typeOfSubscription='" + typeOfSubscription + '\'' +
                ", hasVoted=" + hasVoted +
                '}';
    }
}
