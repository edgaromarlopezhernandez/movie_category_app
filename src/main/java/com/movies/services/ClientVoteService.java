package com.movies.services;

import com.movies.dtos.requests.VoteDataRequest;
import com.movies.exceptions.DataNotFoundException;
import com.movies.exceptions.GeneralException;
import com.movies.exceptions.IncorrectDataBadRequestException;
import com.movies.models.Client;
import com.movies.models.Movie;
import com.movies.repositories.ClientDAO;
import com.movies.repositories.MovieDAO;
import com.movies.services.contracts.ClientVoteInterface;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientVoteService implements ClientVoteInterface {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private MovieDAO movieDAO;


    @Override
    @Transactional
    public Boolean voteUp(VoteDataRequest payload) {
        try{
            checkIfStringIsAValidNumber(payload.getClientId(), "clientId");
            checkIfStringIsAValidNumber(payload.getMovieId(), "movieId");
            Client client = clientDAO.findById(Long.valueOf(payload.getClientId()))
                    .orElseThrow(() -> new DataNotFoundException("Client not found for id: " + payload.getClientId()));
            Movie movie = movieDAO.findById(Long.valueOf(payload.getMovieId()))
                    .orElseThrow(() -> new DataNotFoundException("Movie not found for id: " + payload.getClientId()));

            if(client.getHasVoted() == true) {
                return false;
            }

            client.setHasVoted(true);
            client.setMovieVotedId(Long.valueOf(payload.getMovieId()));
            clientDAO.save(client);
            Long votes = movie.getVotes();
            movie.setVotes(votes + 1);
            movieDAO.save(movie);
            return true;
        }catch (DataNotFoundException | IncorrectDataBadRequestException exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception){
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    @Override
    @Transactional
    public Boolean voteDown(VoteDataRequest payload) {
        try{
            checkIfStringIsAValidNumber(payload.getClientId(), "clientId");
            checkIfStringIsAValidNumber(payload.getMovieId(), "movieId");
            Client client = clientDAO.findById(Long.valueOf(payload.getClientId()))
                    .orElseThrow(() -> new DataNotFoundException("Client not found for id: " + payload.getClientId()));
            Movie movie = movieDAO.findById(Long.valueOf(payload.getMovieId()))
                    .orElseThrow(() -> new DataNotFoundException("Movie not found for id: " + payload.getClientId()));
            if(client.getHasVoted() == null || !client.getHasVoted())
                throw new IncorrectDataBadRequestException("The client with ID: " + payload.getClientId() + " has never voted, therefore a vote down cannot be made.");
            if(Long.valueOf(payload.getMovieId()).equals(client.getMovieVotedId())) {
                client.setHasVoted(false);
                client.setMovieVotedId(null);
                clientDAO.save(client);
                Long votes = movie.getVotes();
                movie.setVotes(votes - 1);
                movieDAO.save(movie);
                return true;
            } else {
                throw new IncorrectDataBadRequestException("Movie to vote down doesn't match the current client's vote");
            }
        }catch (DataNotFoundException | IncorrectDataBadRequestException exception){
            log.info("INFO MESSAGE=> " + exception.getMessage(), exception);
            throw exception;
        }catch (Exception exception) {
            log.error("ERROR MESSAGE=> " + exception.getMessage(), exception);
            throw new GeneralException(exception.getMessage(), exception);
        }
    }

    private void checkIfStringIsAValidNumber(String id, String typeId) {
        if (id == null) {
            throw new IncorrectDataBadRequestException(id + " is not a valid " + typeId);
        }
        try {
            int i = Integer.parseInt(id);
            if(i < 1) {
                throw new IncorrectDataBadRequestException(id + " is not a valid " + typeId);
            }
        } catch (NumberFormatException nfe) {
            throw new IncorrectDataBadRequestException(id + " is not a valid " + typeId);
        }
    }
}
