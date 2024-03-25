package com.movies.services;

import com.movies.dtos.responses.MoviesPaged;
import com.movies.exceptions.DataNotFoundException;
import com.movies.exceptions.GeneralException;
import com.movies.exceptions.IncorrectDataBadRequestException;
import com.movies.models.ImageMovie;
import com.movies.models.Movie;
import com.movies.repositories.MovieDAO;
import com.movies.services.contracts.MovieInterface;
import com.movies.utils.FilePojo;
import com.movies.utils.MovieEntityToDtoConverter;
import com.movies.utils.PageInfo;
import com.movies.utils.PageableHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    public Optional<MoviesPaged> findAllPagedMoviesWithSpecificOrder(Integer pageNumber, Integer pageSize, String order) {
        try{
            Long records = movieDAO.count();
            if(records == 0){
                return Optional.empty();
            }
            return fetchAllMoviesData(pageNumber, pageSize, records, order, null);
        }catch (DataNotFoundException | IncorrectDataBadRequestException exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    @Transactional
    private Optional<MoviesPaged> fetchAllMoviesData(Integer pageNumber, Integer pageSize, Long records, String order, String year){
        try{
            PageInfo pageInfo = pageableHelper.helper(pageNumber, pageSize, records);
            Pageable page = PageRequest.of(pageInfo.getCurrentPage() -1, pageInfo.getPageSize());
            List<Movie> movies;
            order = order.toLowerCase();
            switch(order) {
                case "release_year":
                    log.info("Ordering movies by release year");
                    //List<Movie> movies = movieDAO.findAllMoviesGroupedByReleaseYear(page).toList();
                    movies = movieDAO.findAll(page)
                            .stream()
                            .sorted(Comparator.comparing(Movie::getReleaseYear).reversed())
                            .toList();
                    break;
                case "votes":
                    log.info("Ordering movies by votes");
                    movies = movieDAO.findAll(page)
                            .stream()
                            .sorted(Comparator.comparing(Movie::getVotes).reversed())
                            .toList();
                    break;
                case "single_release_year":
                    log.info("Ordering movies by a single release year");
                    movies = movieDAO.findAllMoviesGroupedBySpecificReleaseYear(page, Integer.valueOf(year))
                            .stream()
                            .sorted(Comparator.comparing(Movie::getReleaseYear).reversed())
                            .toList();
                    break;
                default:
                    throw new IncorrectDataBadRequestException("Movies can not be ordered by: " + order + ". Please use a valid value like: release_year or votes");
            }

            MoviesPaged moviesPaged = MoviesPaged.builder()
                    .movies(converter.convertMovieEntityToDto(movies))
                    .pageInfo(pageInfo)
                    .build();
            return Optional.of(moviesPaged);
        }catch (DataNotFoundException | IncorrectDataBadRequestException exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    @Override
    @Transactional()
    public Optional<MoviesPaged> findAllPagedMoviesBySpecificReleaseYear(Integer pageNumber, Integer pageSize, String order, String year) {
        try{
            checkIfStringIsAValidYear(year);
            Long records = movieDAO.countBySpecificReleaseYear(Integer.valueOf(year));
            if(records == 0){
                return Optional.empty();
            }
            return fetchAllMoviesData(pageNumber, pageSize, records, order, year);
        }catch (DataNotFoundException | IncorrectDataBadRequestException exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    private void checkIfStringIsAValidYear(String year) {
        if (year == null) {
            throw new IncorrectDataBadRequestException(year + " is not a valid year");
        }
        try {
            int i = Integer.parseInt(year);
            if(i > LocalDate.now().getYear() || i < 1900) {
                throw new IncorrectDataBadRequestException(year + " is not a valid year");
            }
        } catch (NumberFormatException nfe) {
            throw new IncorrectDataBadRequestException(year + " is not a valid year");
        }
    }

    public FilePojo getImageById(String movieId) {
        checkIfStringIsAValidNumber(movieId);
        Movie movie = movieDAO.findById(Long.valueOf(movieId))
                .orElseThrow(() -> new DataNotFoundException("Movie not found for id: " + movieId));
        Optional<ImageMovie> optionalImageMovie = Optional.of(movie.getImage());
        return FilePojo
                .builder()
                .name(movie.getName())
                .data(optionalImageMovie.get().getImage())
                .build();
    }

    private void checkIfStringIsAValidNumber(String movieId) {
        if (movieId == null) {
            throw new IncorrectDataBadRequestException(movieId + " is not a valid movieId");
        }
        try {
            int i = Integer.parseInt(movieId);
            if(i < 1) {
                throw new IncorrectDataBadRequestException(movieId + " is not a valid movieId");
            }
        } catch (NumberFormatException nfe) {
            throw new IncorrectDataBadRequestException(movieId + " is not a valid movieId");
        }
    }
}
