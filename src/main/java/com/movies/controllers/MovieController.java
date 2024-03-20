package com.movies.controllers;

import com.movies.dtos.MoviesPaged;
import com.movies.services.MovieService;
import com.movies.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    //https://codebeautify.org/base64-to-image-converter
    @Autowired
    private MovieService service;
    @GetMapping
    public ResponseEntity<?> listAllMoviesByReleaseYear(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize){
        Optional<MoviesPaged> movies = service.findAllPagedMoviesByReleaseYear(pageNumber, pageSize);
        if (!movies.isPresent())
            return new CustomResponse<>(true,"There's no movies",null).createResponse(HttpStatus.NOT_FOUND);
        else
            return new CustomResponse(true, "Success", movies).createResponse();
    }
}
