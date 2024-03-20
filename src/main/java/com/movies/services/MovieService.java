package com.movies.services;

import com.movies.dtos.MoviesPaged;
import com.movies.exceptions.DataNotFoundException;
import com.movies.exceptions.GeneralException;
import com.movies.models.Movie;
import com.movies.repositories.MovieDAO;
import com.movies.services.contracts.MovieInterface;
import com.movies.utils.MovieEntityToDtoConverter;
import com.movies.utils.PageInfo;
import com.movies.utils.PageableHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService implements MovieInterface {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    MovieEntityToDtoConverter converter;

    @Autowired
    private PageableHelper pageableHelper;

    @Override
    @Transactional()
    public Optional<MoviesPaged> findAllPagedMoviesByReleaseYear(Integer pageNumber, Integer pageSize) {
        try{
            Long records = movieDAO.count();
            if(records == 0){
                return Optional.empty();
            }
            System.out.println("Test 1*********************************************************************");
            System.out.println(records);
            return fetchAllMoviesByReleaseYearData(pageNumber, pageSize, records);
        }catch (DataNotFoundException /*| IncorrectDataBadRequestException*/ exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    private Optional<MoviesPaged> fetchAllMoviesByReleaseYearData(Integer pageNumber, Integer pageSize, Long records){
        try{
            System.out.println("Test 2#####################################################################3");
            System.out.println("pageNumber: " + pageNumber);
            System.out.println("pageSize: " + pageSize);
            System.out.println("records: " + records);
            PageInfo pageInfo = pageableHelper.helper(pageNumber, pageSize, records);
            System.out.println("pageInfo:  " + pageInfo);
            Pageable page = PageRequest.of(pageInfo.getCurrentPage() -1, pageInfo.getPageSize());
            //List<Movie> movies = movieDAO.findAllMoviesGroupedByReleaseYear(page).toList();
            List<Movie> movies = movieDAO.findAll(page).stream()
                    .sorted(Comparator.comparing(Movie::getReleaseYear).reversed())
                    .toList();
            MoviesPaged moviesPaged = MoviesPaged.builder()
                    .movies(converter.convertMovieEntityToDto(movies))
                    .pageInfo(pageInfo)
                    .build();
            return Optional.of(moviesPaged);
        }catch (DataNotFoundException /*| IncorrectDataBadRequestException*/ exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }
}
