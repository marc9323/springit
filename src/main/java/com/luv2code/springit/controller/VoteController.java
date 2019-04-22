package com.luv2code.springit.controller;

import com.luv2code.springit.domain.Link;
import com.luv2code.springit.domain.Vote;
import com.luv2code.springit.repository.LinkRepository;
import com.luv2code.springit.repository.VoteRepository;
import com.luv2code.springit.service.LinkService;
import com.luv2code.springit.service.VoteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    //private VoteRepository voteRepository; replaced by voteservice
    private VoteService voteService;
    //private LinkRepository linkRepository; // replaced by linkservice
    private final LinkService linkService;

    public VoteController(VoteService voteService, LinkService linkService){
        this.voteService = voteService;
        this.linkService= linkService;
    }

    // http://localhost:8080/vote/link/1/direction/-1/votecount/5
    @Secured({"ROLE_USER"}) // only a user is allowed to vote here.
    @GetMapping("/vote/link/{linkID}/direction/{direction}/votecount/{voteCount}")
    public int vote(@PathVariable Long linkID, @PathVariable short direction, @PathVariable int voteCount) {
       Optional<Link> optionalLink = linkService.findById(linkID);
       if(optionalLink.isPresent()){
           Link link = optionalLink.get();
           Vote vote = new Vote(direction, link);
           voteService.save(vote);

           int updatedVoteCount = voteCount + direction;
           link.setVoteCount(updatedVoteCount);
           linkService.save(link); // is it necessary to call save here?

           return updatedVoteCount;
       }

       return voteCount; // if vote count is too high and link not present?
    }
}

