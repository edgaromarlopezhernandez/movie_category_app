package com.movies.services.contracts;

import com.movies.dtos.requests.VoteDataRequest;

public interface ClientVoteInterface {

    Boolean voteUp(VoteDataRequest payload);
    Boolean voteDown(VoteDataRequest payload);
}
