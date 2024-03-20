package com.movies.dtos;

import com.movies.utils.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MoviesPaged {
    public List<MovieResponse> movies;
    public PageInfo pageInfo;
}
