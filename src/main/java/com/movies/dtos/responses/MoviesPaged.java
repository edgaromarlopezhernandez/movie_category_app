package com.movies.dtos.responses;

import com.movies.utils.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MoviesPaged implements Serializable {
    public List<MovieResponse> movies;
    public PageInfo pageInfo;
}
