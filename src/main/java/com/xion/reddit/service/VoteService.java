package com.xion.reddit.service;

import com.xion.reddit.model.Vote;
import com.xion.reddit.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
