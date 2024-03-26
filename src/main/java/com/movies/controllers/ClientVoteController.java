package com.movies.controllers;

import com.movies.dtos.requests.VoteDataRequest;
import com.movies.services.ClientVoteService;
import com.movies.utils.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Votes", description = "The votes API")
@RestController
@RequestMapping("/vote")
public class ClientVoteController {

    @Autowired
    private ClientVoteService service;

    @Operation(
            summary = "Vote up a movie",
            description = "Voting is achieved by indicating the id (String type) of the customer who wants to vote for a movie and the id of the movie they want to vote for. If either of the two values does not exist, it throws an exception.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully vote up"),
            @ApiResponse(responseCode = "400", description = "You'll get this code if the client has voted up before")
    })
    @PostMapping("/up")
    public ResponseEntity<?> voteUp(@RequestBody VoteDataRequest payload) {
        Boolean response = service.voteUp(payload);
        if (response)
            return new CustomResponse<>(true, "Your vote was registered for movie with id: " + payload.getMovieId() + " by client with id: " + payload.getClientId(), null).createResponse(HttpStatus.CREATED);
        else
            return new CustomResponse<>(false,"Please vote down before make a vote up",null).createResponse(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Vote down a movie",
            description = "Voting down is achieved by indicating the id (String type) of the customer who wants to vote for a movie and the id of the movie they want to vote for. If either of the two values does not exist, it throws an exception.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successfully vote up"),
    })
    @PostMapping("/down")
    public ResponseEntity<?> voteDown(@RequestBody VoteDataRequest payload) {
        Boolean response = service.voteDown(payload);
        return new CustomResponse<>(true, "Voted down for movie with id: " + payload.getMovieId() + " by client with id: " + payload.getClientId(), response).createResponse();
    }
}