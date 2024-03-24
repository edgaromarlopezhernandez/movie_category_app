package com.movies.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageMovieResponse implements Serializable {
    private Long idImageMovie;
    private byte[] image;
}
