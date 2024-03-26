package com.movies.services;

import com.movies.dtos.requests.VoteDataRequest;
import com.movies.exceptions.DataNotFoundException;
import com.movies.exceptions.IncorrectDataBadRequestException;
import com.movies.models.Client;
import com.movies.models.Movie;
import com.movies.repositories.ClientDAO;
import com.movies.repositories.MovieDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class ClientVoteServiceTest {

    @InjectMocks
    private ClientVoteService service;

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private MovieDAO movieDAO;

    @DisplayName("Should return true if user can vote up")
    @Test
    void shouldReturnTrueIfUserCanVoteUp() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("1")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(false)
                .movieVotedId(null)
                .build();
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
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Mockito.when(clientDAO.save(Mockito.any(Client.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(movieDAO.save(Mockito.any(Movie.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        boolean result = service.voteUp(voteDataRequest);
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, movie.getVotes());
    }

    @DisplayName("Should throw data not found exception when client not found")
    @Test
    void ShouldThrowDataNotFoundExceptionWhenClientNotFound() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("1")
                .movieId("77")
                .build();
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.empty());
        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> service.voteUp(voteDataRequest));
        //Assert
        Assertions.assertEquals("Client not found for id: 1", exception.getMessage());
    }

    @DisplayName("Should throw DataNotFoundException when movie not found")
    @Test
    void ShouldThrowDataNotFoundExceptionWhenMovieNotFound() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("1")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(false)
                .movieVotedId(null)
                .build();
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.empty());
        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> service.voteUp(voteDataRequest));
        //Assert
        Assertions.assertEquals("Movie not found for id: 1", exception.getMessage());
    }

    @DisplayName("Should return false if user can't vote up")
    @Test
    void shouldReturnFalseIfUserCanNotVoteUp() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("1")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(true)
                .movieVotedId(null)
                .build();
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
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        boolean result = service.voteUp(voteDataRequest);
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result);
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when client id is null")
    @Test
    void ShouldThrowIncorrectDataBadRequestExceptionWhenClientIdIsNull() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId(null)
                .movieId("77")
                .build();
        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> service.voteUp(voteDataRequest));
        //Assert
        Assertions.assertEquals("null is not a valid clientId", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when client id is negative")
    @Test
    void ShouldThrowIncorrectDataBadRequestExceptionWhenClientIdIsNegative() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("-1")
                .movieId("77")
                .build();
        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> service.voteUp(voteDataRequest));
        //Assert
        Assertions.assertEquals("-1 is not a valid clientId", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when client id is not numeric")
    @Test
    void ShouldThrowIncorrectDataBadRequestExceptionWhenClientIdIsNotNumeric() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("not_numeric_value")
                .movieId("77")
                .build();
        //Act
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> service.voteUp(voteDataRequest));
        //Assert
        Assertions.assertEquals("not_numeric_value is not a valid clientId", exception.getMessage());
    }

    @DisplayName("Should return true if user can vote down")
    @Test
    void shouldReturnTrueIfUserCanVoteDown() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("3311")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(true)
                .movieVotedId(77L)
                .build();
        Movie movie = Movie
                .builder()
                .idMovie(77L)
                .name("Movie Test 1")
                .description("Description of movie test 1")
                .releaseYear(LocalDate.of(2022, 12, 10))
                .genres("Action")
                .votes(5L)
                .image(null)
                .build();
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        Mockito.when(clientDAO.save(Mockito.any(Client.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(movieDAO.save(Mockito.any(Movie.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        boolean result = service.voteDown(voteDataRequest);
        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result);
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when client has voted is null")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenClientHasVotedIsNull() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("3311")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(null)
                .movieVotedId(null)
                .build();
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
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> service.voteDown(voteDataRequest));
        //Assert
        Assertions.assertEquals("The client with ID: 3311 has never voted, therefore a vote down cannot be made.", exception.getMessage());
    }

    @DisplayName("Should throw IncorrectDataBadRequestException when movie id doesn't match")
    @Test
    void shouldThrowIncorrectDataBadRequestExceptionWhenMovieIdDoesNotMatch() {
        //Arrange
        VoteDataRequest voteDataRequest = VoteDataRequest
                .builder()
                .clientId("3311")
                .movieId("77")
                .build();
        Client client = Client
                .builder()
                .idClient(3311L)
                .fullName("Client full nate test")
                .typeOfSubscription("Premium")
                .hasVoted(true)
                .movieVotedId(1L)
                .build();
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
        //Act
        Mockito.when(clientDAO.findById(anyLong())).thenReturn(Optional.ofNullable(client));
        Mockito.when(movieDAO.findById(anyLong())).thenReturn(Optional.ofNullable(movie));
        IncorrectDataBadRequestException exception = Assertions.assertThrows(IncorrectDataBadRequestException.class, () -> service.voteDown(voteDataRequest));
        //Assert
        Assertions.assertEquals("Movie to vote down doesn't match the current client's vote", exception.getMessage());
    }
}
