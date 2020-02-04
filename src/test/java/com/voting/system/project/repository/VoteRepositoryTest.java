package com.voting.system.project.repository;

import com.voting.system.project.model.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.VoteTestUtil.checkSave;
import static com.voting.system.project.util.VoteTestUtil.checkUpdate;

class VoteRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void save() {
        Vote saved = voteRepository.save(getNewVote());
        checkSave(saved);
    }

    @Test
    void update() {
        Vote updated = voteRepository.save(getUpdatedVote(VOTE_USER_2));
        checkUpdate(updated);
    }

    @Test
    void findVoteByUserIdOnCurrentDate() {
        Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_2);
        assertMatch(actual, VOTE_USER_2);
    }

    @Test
    void findVoteByUserIdOnCurrentDateNotExist() {
        Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_1);
        Assertions.assertNull(actual);
    }
}