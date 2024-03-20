package com.movies.services.contracts;

import com.movies.dtos.MoviesPaged;

import java.util.Optional;

public interface MovieInterface {
    Optional<MoviesPaged> findAllPagedMoviesByReleaseYear(Integer pageNumber, Integer pageSize);
}
