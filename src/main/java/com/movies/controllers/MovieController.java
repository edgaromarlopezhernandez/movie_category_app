package com.movies.controllers;

import com.movies.dtos.responses.MoviesPaged;
import com.movies.services.MovieService;
import com.movies.utils.CustomResponse;
import com.movies.utils.FilePojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Tag(name = "Movies", description = "The movies API")
@RestController
@RequestMapping("/movies")
public class MovieController {

    //https://codebeautify.org/base64-to-image-converter
    @Autowired
    private MovieService service;
    @Operation(
            summary = "Fetch all movies sorted by release_year or votes.",
            description = "Fetches all movie entities sorted by 2 different order type as a query parameter: votes / release_year.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
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

    @Operation(
            summary = "Fetch all movies sorted by a specific release_year.",
            description = "Fetches all movie entities sorted by a specific release_year.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/by-specific-year")
    public ResponseEntity<?> listAllMoviesBySpecificYear(@RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                                      @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
                                                      @RequestParam(name = "year", required = false, defaultValue = "2023") String year) {
        Optional<MoviesPaged> movies = service.findAllPagedMoviesBySpecificReleaseYear(pageNumber, pageSize, "single_release_year", year);
        if (!movies.isPresent())
            return new CustomResponse<>(true,"There's no movies for release year: " + year,null).createResponse(HttpStatus.NOT_FOUND);
        else
            return new CustomResponse<>(true, "Success", movies).createResponse();
    }

    @Operation(
            summary = "Fetch an image by movie id.",
            description = "Fetches an image in a attachment by movie id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
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