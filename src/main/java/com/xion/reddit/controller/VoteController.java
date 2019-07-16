package com.xion.reddit.controller;

import com.xion.reddit.model.Link;
import com.xion.reddit.model.Vote;
import com.xion.reddit.repository.VoteRepository;
import com.xion.reddit.repository.LinkRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private LinkRepository linkRepository;
    private VoteRepository voteRepository;

    public VoteController(LinkRepository linkRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/vote/link/{linkId}/direction/{direction}")
    @Secured({"ROLE_USER"})
    public int vote(@PathVariable Long linkId, @PathVariable short direction) {
        Optional<Link> optionalLink = linkRepository.findById(linkId);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteRepository.save(vote);

            int newVoteCount = link.getVoteCount() + direction;
            link.setVoteCount(newVoteCount);
            linkRepository.save(link);
            return newVoteCount;
        }
        return 0;
    }
}
