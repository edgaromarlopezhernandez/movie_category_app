package com.movies.utils;


import com.movies.dtos.MovieResponse;
import com.movies.models.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieEntityToDtoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public MovieResponse convertMovieEntityToDto (Movie movie){
        return modelMapper.map(movie,MovieResponse.class);
    }

    public List<MovieResponse> convertMovieEntityToDto (List<Movie> movies){
        return movies.parallelStream()
                .map(movie -> convertMovieEntityToDto(movie))
                .collect(Collectors.toList());
    }
}
