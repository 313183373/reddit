package com.xion.reddit.controller;

import com.xion.reddit.model.Link;
import com.xion.reddit.model.Vote;
import com.xion.reddit.service.LinkService;
import com.xion.reddit.service.VoteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private LinkService linkService;
    private VoteService voteService;

    public VoteController(LinkService linkService, VoteService voteService) {
        this.linkService = linkService;
        this.voteService = voteService;
    }

    @GetMapping("/vote/link/{linkId}/direction/{direction}")
    @Secured({"ROLE_USER"})
    public int vote(@PathVariable Long linkId, @PathVariable short direction) {
        Optional<Link> optionalLink = linkService.findById(linkId);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);

            int newVoteCount = link.getVoteCount() + direction;
            link.setVoteCount(newVoteCount);
            linkService.save(link);
            return newVoteCount;
        }
        return 0;
    }
}
