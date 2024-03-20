package com.movies.repositories;

import com.movies.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Long> {
}
