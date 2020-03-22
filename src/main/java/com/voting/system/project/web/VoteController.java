package com.voting.system.project.web;

import com.voting.system.project.service.VoteService;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.voting.system.project.util.ControllerUtil.getResponseEntity;
import static com.voting.system.project.web.RestaurantController.REST_URL;

@Log4j2
@RestController
@RequestMapping(value = VoteController.VOTE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String VOTE_REST_URL = REST_URL + "/{restId}/votes";

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public VoteTo get(@RequestParam("date")
                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int userId = SecurityUtil.authUserId();
        log.info("get vote for user with id {} on date {}", userId, date);
        return voteService.get(userId, date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@PathVariable int restId,
                                                     @RequestBody(required = false) LocalDate date) {
        int userId = SecurityUtil.authUserId();
        log.info("create vote for user with id {} and restaurant with id {} on date {}", userId, restId, date);
        final VoteTo created = voteService.create(userId, restId, date);
        return getResponseEntity(created, VOTE_REST_URL + "?" + date, restId, created.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restId,
                       @RequestBody LocalDateTime dateTime) {
        int userId = SecurityUtil.authUserId();
        log.info("update vote for user with id {} and restaurant with id {} on date {}", userId, restId, dateTime);
        voteService.update(userId, restId, dateTime);
    }
}