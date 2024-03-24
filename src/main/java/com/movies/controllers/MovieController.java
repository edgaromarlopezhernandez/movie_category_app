package com.movies.controllers;

import com.movies.dtos.MoviesPaged;
import com.movies.services.MovieService;
import com.movies.utils.CustomResponse;
import com.movies.utils.FilePojo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    //https://codebeautify.org/base64-to-image-converter
    @Autowired
    private MovieService service;
    @GetMapping
    public ResponseEntity<?> listAllMoviesByReleaseYear(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                        @RequestParam(name = "order", required = false, defaultValue = "release_year") String order){
        Optional<MoviesPaged> movies = service.findAllPagedMoviesWithSpecificOrder(pageNumber, pageSize, order);
        if (!movies.isPresent())
            return new CustomResponse<>(true,"There's no movies",null).createResponse(HttpStatus.NOT_FOUND);
        else
            return new CustomResponse<>(true, "Success", movies).createResponse();
    }

    @GetMapping("/by-specific-year")
    public ResponseEntity listAllMoviesBySpecificYear(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                      @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                      @RequestParam(name = "year", required = false, defaultValue = "2023") String year) {
        Optional<MoviesPaged> movies = service.findAllPagedMoviesBySpecificReleaseYear(pageNumber, pageSize, "single_release_year", year);
        if (!movies.isPresent())
            return new CustomResponse<>(true,"There's no movies for release year: " + year,null).createResponse(HttpStatus.NOT_FOUND);
        else
            return new CustomResponse<>(true, "Success", movies).createResponse();
    }

    @GetMapping("/{movieId}")
    public void listSpecificMovie(HttpServletResponse response, @PathVariable String movieId) throws Exception {
            FilePojo filePojo = service.getImageById(movieId);
            streamReport(response, filePojo.getData(), filePojo.getName());
    }

    private void streamReport(HttpServletResponse response, byte[] data, String name)
            throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setHeader("Content-disposition", "attachment; filename=" + name);
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
