package com.voting.system.project.service;

import com.voting.system.project.model.Vote;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.NotExistException;
import com.voting.system.project.util.exception.VoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.ValidationUtil.VOTE_MAX_TIME;
import static com.voting.system.project.util.VoteTestUtil.checkSave;
import static com.voting.system.project.util.VoteTestUtil.checkUpdate;
import static com.voting.system.project.util.VoteUtil.getToFrom;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void save() {
        checkIfRunTest();
        final VoteTo voteTo = getToFrom(getNewVote());
        Vote saved = voteService.createOrUpdate(voteTo);
        checkSave(saved);
    }

    @Test
    void update() {
        checkIfRunTest();
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
    void createOrUpdateUserNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> voteService.createOrUpdate(new VoteTo(null, NOT_EXIST_ID, RESTAURANT_ID_3)));
    }

    @Test
    void createOrUpdateRestaurantNotExist() {
        Assertions.assertThrows(NotExistException.class, () -> voteService.createOrUpdate(new VoteTo(VOTE_ID_1, USER_ID_1, NOT_EXIST_ID)));
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
        assumeTrue(LocalTime.now().compareTo(VOTE_MAX_TIME) > 0,
                "text execution time is from " + VOTE_MAX_TIME + "AM till 23:59:59AM");
        Vote vote = getNewVote();
        final VoteException exception = Assertions.assertThrows(VoteException.class,
                () -> voteService.createOrUpdate(getToFrom(vote)));
        Assertions.assertEquals("vote can't be accepted after " + VOTE_MAX_TIME + "AM", exception.getMessage());
    }

    private void checkIfRunTest() {
        assumeTrue(LocalTime.now().compareTo(VOTE_MAX_TIME) <= 0,
                "text execution time is from 00:00:00AM till " + VOTE_MAX_TIME + "AM");
    }
}