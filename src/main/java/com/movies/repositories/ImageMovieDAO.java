package com.movies.repositories;

import com.movies.models.ImageMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMovieDAO extends JpaRepository<ImageMovie, Long> {
}
