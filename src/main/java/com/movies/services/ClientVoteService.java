package com.movies.services;

import com.movies.dtos.requests.VoteDataRequest;
import com.movies.exceptions.DataNotFoundException;
import com.movies.exceptions.IncorrectDataBadRequestException;
import com.movies.models.Client;
import com.movies.models.Movie;
import com.movies.repositories.ClientDAO;
import com.movies.repositories.MovieDAO;
import com.movies.services.contracts.ClientVoteInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientVoteService implements ClientVoteInterface {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private MovieDAO movieDAO;


    @Override
    @Transactional
    public Boolean voteUp(VoteDataRequest payload) {
        //TODO: Validate numeric ids for movie and client
        Client client = clientDAO.findById(Long.valueOf(payload.getClientId()))
                .orElseThrow(() -> new DataNotFoundException("Client not found for id: " + payload.getClientId()));
        Movie movie = movieDAO.findById(Long.valueOf(payload.getMovieId()))
                .orElseThrow(() -> new DataNotFoundException("Movie not found for id: " + payload.getClientId()));

        if(client.getHasVoted() == true) {
            return false;
        }

        client.setHasVoted(true);
        Long votes = movie.getVotes();
        movie.setVotes(votes + 1);
        return true;

    }

    @Override
    public Boolean voteDown(VoteDataRequest payload) {
        return null;
    }
}
