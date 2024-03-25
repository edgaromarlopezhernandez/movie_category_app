package com.movies.services;

import com.movies.dtos.responses.MovieResponse;
import com.movies.dtos.responses.MoviesPaged;
import com.movies.exceptions.IncorrectDataBadRequestException;
import com.movies.models.ImageMovie;
import com.movies.models.Movie;
import com.movies.repositories.MovieDAO;
import com.movies.utils.FilePojo;
import com.movies.utils.MovieEntityToDtoConverter;
import com.movies.utils.PageInfo;
import com.movies.utils.PageableHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieDAO movieDAO;

    @Mock
    private PageableHelper pageableHelper;

    @Mock
    MovieEntityToDtoConverter converter;

    @DisplayName("Should return empty optional when no records in Database")
    @Test
    void shouldReturnEmptyOptionalWhenNoRecordsInDatabase() {
        //Arrange

        //Act
        Optional<MoviesPaged> response = movieService.findAllPagedMoviesWithSpecificOrder(1, 10, "release_year");
        Mockito.when(movieDAO.count()).thenReturn(0L);
        //Assert
        Assertions.assertEquals(Optional.empty(), response);
    }


    @DisplayName("Should return movies ordered by release year")
    @Test
    void shouldReturnMoviesOrderedByReleaseYear() {
        //Arrange
        Long totalRecords = 3L;
        PageInfo pageInfo = PageInfo
                .builder()
                .currentPage(1)
                .nextPage(1)
                .pageSize(3)
                .previousPage(1)
                .totalPages(1L)
                .totalRecords(3L)
                .build();
        List<Movie> movies = new ArrayList<>();
        Movie movie = Movie
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        movies.add(movie);
        Movie movie2 = Movie
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        movies.add(movie2);
        Movie movie3 = Movie
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        movies.add(movie3);

        List<MovieResponse> responseList = new ArrayList<>();
        MovieResponse movieResponse1 = MovieResponse
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        responseList.add(movieResponse1);
        MovieResponse movieResponse2 = MovieResponse
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        responseList.add(movieResponse2);
        MovieResponse movieResponse3 = MovieResponse
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        responseList.add(movieResponse3);
        //Act
        Mockito.when(movieDAO.count()).thenReturn(totalRecords);
        Mockito.when(pageableHelper.helper(anyInt(), anyInt(), anyLong())).thenReturn(pageInfo);
        Page<Movie> moviePage = new PageImpl<>(movies);
        Mockito.when(movieDAO.findAll(Mockito.any(Pageable.class))).thenReturn(moviePage);
        Mockito.when(converter.convertMovieEntityToDto(Mockito.anyList())).thenReturn(responseList);
        Optional<MoviesPaged> response = movieService.findAllPagedMoviesWithSpecificOrder(1, 3, "release_year");
        MoviesPaged moviesPaged = response.get();
        //Assert
        Assertions.assertNotNull(moviesPaged);
        Assertions.assertEquals(totalRecords, moviesPaged.getMovies().size());
    }

    @DisplayName("Should return movies ordered by votes")
    @Test
    void shouldReturnMoviesOrderedByVotes() {
        //Arrange
        Long totalRecords = 3L;
        PageInfo pageInfo = PageInfo
                .builder()
                .currentPage(1)
                .nextPage(1)
                .pageSize(3)
                .previousPage(1)
                .totalPages(1L)
                .totalRecords(3L)
                .build();
        List<Movie> movies = new ArrayList<>();
        Movie movie = Movie
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        movies.add(movie);
        Movie movie2 = Movie
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        movies.add(movie2);
        Movie movie3 = Movie
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        movies.add(movie3);

        List<MovieResponse> responseList = new ArrayList<>();
        MovieResponse movieResponse1 = MovieResponse
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        responseList.add(movieResponse1);
        MovieResponse movieResponse2 = MovieResponse
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        responseList.add(movieResponse2);
        MovieResponse movieResponse3 = MovieResponse
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        responseList.add(movieResponse3);
        //Act
        Mockito.when(movieDAO.count()).thenReturn(totalRecords);
        Mockito.when(pageableHelper.helper(anyInt(), anyInt(), anyLong())).thenReturn(pageInfo);
        Page<Movie> moviePage = new PageImpl<>(movies);
        Mockito.when(movieDAO.findAll(Mockito.any(Pageable.class))).thenReturn(moviePage);
        Mockito.when(converter.convertMovieEntityToDto(Mockito.anyList())).thenReturn(responseList);
        Optional<MoviesPaged> response = movieService.findAllPagedMoviesWithSpecificOrder(1, 3, "votes");
        MoviesPaged moviesPaged = response.get();
        //Assert
        Assertions.assertNotNull(moviesPaged);
        Assertions.assertEquals(totalRecords, moviesPaged.getMovies().size());
    }

    @DisplayName("Should return empty optional when no records in Database trying to find records by specific release year")
    @Test
    void shouldReturnEmptyOptionalWhenNoRecordsInDatabaseInBySpecificReleaseYearMethod() {
        //Arrange

        //Act
        Optional<MoviesPaged> response = movieService.findAllPagedMoviesBySpecificReleaseYear(1, 10, "single_release_year", "2022");
        Mockito.when(movieDAO.countBySpecificReleaseYear(anyInt())).thenReturn(0L);
        //Assert
        Assertions.assertEquals(Optional.empty(), response);
    }

    @DisplayName("Should return movies ordered by specific release year")
    @Test
    void shouldReturnMoviesOrderedBySpecificReleaseYear() {
        //Arrange
        Long totalRecords = 3L;
        PageInfo pageInfo = PageInfo
                .builder()
                .currentPage(1)
                .nextPage(1)
                .pageSize(3)
                .previousPage(1)
                .totalPages(1L)
                .totalRecords(3L)
                .build();
        List<Movie> movies = new ArrayList<>();
        Movie movie = Movie
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        movies.add(movie);
        Movie movie2 = Movie
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        movies.add(movie2);
        Movie movie3 = Movie
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        movies.add(movie3);

        List<MovieResponse> responseList = new ArrayList<>();
        MovieResponse movieResponse1 = MovieResponse
                .builder()
                .idMovie(1L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        responseList.add(movieResponse1);
        MovieResponse movieResponse2 = MovieResponse
                .builder()
                .idMovie(2L)
                .name("Movie Test 2")
                .description("Description of movie test 2")
                .releaseYear(LocalDate.of(2022, 11, 10))
                .genres("Drama")
                .votes(6L)
                .image(null)
                .build();
        responseList.add(movieResponse2);
        MovieResponse movieResponse3 = MovieResponse
                .builder()
                .idMovie(3L)
                .name("Movie Test 3")
                .description("Description of movie test 3")
                .releaseYear(LocalDate.of(2022, 10, 10))
                .genres("Horror")
                .votes(7L)
                .image(null)
                .build();
        responseList.add(movieResponse3);
        //Act
        Mockito.when(movieDAO.countBySpecificReleaseYear(anyInt())).thenReturn(totalRecords);
        Mockito.when(pageableHelper.helper(anyInt(), anyInt(), anyLong())).thenReturn(pageInfo);
        Page<Movie> moviePage = new PageImpl<>(movies);
        Mockito.when(movieDAO.findAllMoviesGroupedBySpecificReleaseYear(Mockito.any(Pageable.class), anyInt())).thenReturn(moviePage);
        Mockito.when(converter.convertMovieEntityToDto(Mockito.anyList())).thenReturn(responseList);
        Optional<MoviesPaged> response = movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "single_release_year", "2022");
        MoviesPaged moviesPaged = response.get();
        //Assert
        Assertions.assertNotNull(moviesPaged);
        Assertions.assertEquals(totalRecords, moviesPaged.getMovies().size());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when order value not defined")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenOrderValueNotDefined() {
        //Arrange
        Long totalRecords = 3L;
        PageInfo pageInfo = PageInfo
                .builder()
                .currentPage(1)
                .nextPage(1)
                .pageSize(3)
                .previousPage(1)
                .totalPages(1L)
                .totalRecords(3L)
                .build();
        //Act
        Mockito.when(movieDAO.countBySpecificReleaseYear(anyInt())).thenReturn(totalRecords);
        Mockito.when(pageableHelper.helper(anyInt(), anyInt(), anyLong())).thenReturn(pageInfo);
        //Assert
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "order_value_not_defined_in_service", "2022"));
        Assertions.assertEquals("Movies can not be ordered by: order_value_not_defined_in_service. Please use a valid value like: release_year or votes", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when String year to valid is not a valid year")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenStringYearToValidIsNotAValidYear() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "single_release_year", "1899"));
        //Assert
        Assertions.assertEquals("1899 is not a valid year", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when String year to valid is not a valid year (second test)")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenStringYearToValidIsNotAValidYear2() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "single_release_year", "2034"));
        //Assert
        Assertions.assertEquals("2034 is not a valid year", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when String year to valid is not numeric value")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenStringYearToValidIsNotANumericValue() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "single_release_year", "not_numeric_value"));
        //Assert
        Assertions.assertEquals("not_numeric_value is not a valid year", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when String year to valid is null")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenStringYearToValidIsNull() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.findAllPagedMoviesBySpecificReleaseYear(1, 3, "single_release_year", null));
        //Assert
        Assertions.assertEquals("null is not a valid year", exception.getMessage());
    }

    @DisplayName("Should return a FilePojo by image Id")
    @Test
    void shouldReturnFilePojoByImageId() {
        //Arrange
        ImageMovie imageMovie = ImageMovie
                .builder()
                .idImageMovie(22L)
                .image("/9j/4AAQSkZJRgABAQEAYABgAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2ODApLCBxdWFsaXR5ID0gNzUK/9sAQwAIBgYHBgUIBwcHCQkICgwUDQwLCwwZEhMPFB0aHx4dGhwcICQuJyAiLCMcHCg3KSwwMTQ0NB8nOT04MjwuMzQy/9sAQwEJCQkMCwwYDQ0YMiEcITIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIy/8AAEQgEsANBAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2".getBytes())
                .build();

        Movie movie = Movie
                .builder()
                .idMovie(3323L)
                .name("Cocaine bear")
                .description("Description for cocaine bear movie")
                .releaseYear(LocalDate.of(2023, 02, 24))
                .genres("Comedy")
                .votes(10L)
                .image(imageMovie)
                .build();
        //Act
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        FilePojo file = movieService.getImageById("3323");
        //Assert
        Assertions.assertNotNull(file);
        Assertions.assertEquals(movie.getName(), file.getName());
        Assertions.assertEquals(imageMovie.getImage(), file.getData());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when movie id is null")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenMovieIdIsNull() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.getImageById(null));
        //Assert
        Assertions.assertEquals("null is not a valid movieId", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when movie id is negative and not valid")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenMovieIdIsNegativeAndNotValid() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.getImageById("-1"));
        //Assert
        Assertions.assertEquals("-1 is not a valid movieId", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when movie id is not numeric")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenMovieIdIsNotNumeric() {
        //Arrange

        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> movieService.getImageById("not_numeric_value"));
        //Assert
        Assertions.assertEquals("not_numeric_value is not a valid movieId", exception.getMessage());
    }
}
