package com.movies.repositories;

import com.movies.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client, Long> {
}
