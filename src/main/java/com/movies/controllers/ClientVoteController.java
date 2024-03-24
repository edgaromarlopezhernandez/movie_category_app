package com.movies.controllers;

import com.movies.dtos.requests.VoteDataRequest;
import com.movies.services.ClientVoteService;
import com.movies.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vote")
public class ClientVoteController {

    @Autowired
    private ClientVoteService service;

    @PostMapping("/up")
    public ResponseEntity<?> voteUp(@RequestBody VoteDataRequest payload) {
        Boolean response = service.voteUp(payload);
        if (response)
            return new CustomResponse<>(true, "Your vote was registered for movie with id: " + payload.getMovieId() + " by client with id: " + payload.getClientId(), null).createResponse();
        else
            return new CustomResponse<>(false,"Please vote down before make a vote up",null).createResponse(HttpStatus.NOT_FOUND);
    }
}