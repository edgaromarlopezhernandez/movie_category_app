package com.movies.services.contracts;

import com.movies.dtos.responses.MoviesPaged;

import java.util.Optional;

public interface MovieInterface {

    Optional<MoviesPaged> findAllPagedMoviesWithSpecificOrder(Integer pageNumber, Integer pageSize, String order);
    Optional<MoviesPaged> findAllPagedMoviesBySpecificReleaseYear(Integer pageNumber, Integer pageSize, String order, String year);
}
