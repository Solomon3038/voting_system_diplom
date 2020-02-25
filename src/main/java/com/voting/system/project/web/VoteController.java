package com.voting.system.project.web;

import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.service.VoteService;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.voting.system.project.util.ValidationUtil.checkNew;
import static com.voting.system.project.web.RestaurantController.REST_URL;

@Log4j2
@RestController
@RequestMapping(value = VoteController.VOTE_URL)
public class VoteController {

    public static final String VOTE_URL = REST_URL + "/{restId}/votes";

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@PathVariable int restId) {
        int userId = SecurityUtil.authUserId();
        log.info("create vote for user with id {} and restaurant with id {}", userId, restId);
        final VoteTo voteTo = new VoteTo(userId, restId);
        checkNew(voteTo);
        voteService.createOrUpdate(voteTo);
    }
}