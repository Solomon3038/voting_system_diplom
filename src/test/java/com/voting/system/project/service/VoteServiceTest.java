package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.model.Vote;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.voting.system.project.TestDataHelper.VOTE_USER_2;
import static com.voting.system.project.TestDataHelper.getNewVote;
import static com.voting.system.project.TestDataHelper.getUpdatedVote;
import static com.voting.system.project.util.ValidationUtil.VOTE_MAX_TIME;
import static com.voting.system.project.util.VoteTestUtil.checkIfAfterTime;
import static com.voting.system.project.util.VoteTestUtil.checkIfBeforeTime;
import static com.voting.system.project.util.VoteTestUtil.checkSave;
import static com.voting.system.project.util.VoteTestUtil.checkUpdate;
import static com.voting.system.project.util.VoteUtil.getToFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteServiceTest extends AbstractTest {

    @Autowired
    private VoteService voteService;

    @Test
    void save() {
        checkIfBeforeTime();
        final VoteTo voteTo = getToFrom(getNewVote());
        final Vote saved = voteService.createOrUpdate(voteTo);
        checkSave(saved);
    }

    @Test
    void update() {
        checkIfBeforeTime();
        final VoteTo voteTo = getToFrom(getUpdatedVote(VOTE_USER_2));
        final Vote updated = voteService.createOrUpdate(voteTo);
        checkUpdate(updated);
    }

    @Test
    void createOrUpdateNullError() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> voteService.createOrUpdate(null));
        assertEquals("vote must not be null", exception.getMessage());
    }

    @Test
    void createOrUpdateNotCurrentDate() {
        final Vote vote = getNewVote();
        vote.setDate(LocalDate.MIN);
        final VoteException lessDateException = assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        assertEquals("vote date " + vote.getDate() + " must be equal current date", lessDateException.getMessage());
        vote.setDate(LocalDate.MAX);
        final VoteException greaterDateException = assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        assertEquals("vote date " + vote.getDate() + " must be equal current date", greaterDateException.getMessage());
    }

    @Test
    void createOrUpdateNotInTime() {
        checkIfAfterTime();
        final Vote vote = getNewVote();
        final VoteException exception = assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        assertEquals("vote can't be accepted after " + VOTE_MAX_TIME + "AM", exception.getMessage());
    }
}