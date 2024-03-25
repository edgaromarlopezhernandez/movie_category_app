package com.movies.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MovieResponse implements Serializable {
    private Long idMovie;
    private String name;
    private String description;
    private LocalDate releaseYear;
    private String genres;
    private Long votes;
    private ImageMovieResponse image;
}
