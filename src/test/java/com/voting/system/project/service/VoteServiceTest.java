package com.voting.system.project.service;

import com.voting.system.project.model.Vote;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.VoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.ValidationUtil.VOTE_MAX_TIME;
import static com.voting.system.project.util.VoteTestUtil.*;
import static com.voting.system.project.util.VoteUtil.getToFrom;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void save() {
        checkIfBeforeTime();
        final VoteTo voteTo = getToFrom(getNewVote());
        Vote saved = voteService.createOrUpdate(voteTo);
        checkSave(saved);
    }

    @Test
    void update() {
        checkIfBeforeTime();
        final VoteTo voteTo = getToFrom(getUpdatedVote(VOTE_USER_2));
        Vote updated = voteService.createOrUpdate(voteTo);
        checkUpdate(updated);
    }

    @Test
    void createOrUpdateNullError() {
        final IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> voteService.createOrUpdate(null));
        Assertions.assertEquals("vote must not be null", exception.getMessage());
    }

    @Test
    void createOrUpdateNotCurrentDate() {
        Vote vote = getNewVote();
        vote.setDate(LocalDate.MIN);
        final VoteException lessDateException = Assertions.assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        Assertions.assertEquals("vote date " + vote.getDate() + " must be equal current date", lessDateException.getMessage());
        vote.setDate(LocalDate.MAX);
        final VoteException greaterDateException = Assertions.assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        Assertions.assertEquals("vote date " + vote.getDate() + " must be equal current date", greaterDateException.getMessage());
    }

    @Test
    void createOrUpdateNotInTime() {
        checkIfAfterTime();
        Vote vote = getNewVote();
        final VoteException exception = Assertions.assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        Assertions.assertEquals("vote can't be accepted after " + VOTE_MAX_TIME + "AM", exception.getMessage());
    }
}