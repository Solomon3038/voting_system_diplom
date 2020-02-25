package com.voting.system.project.repository;

import com.voting.system.project.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestDataHelper.USER_ID_1;
import static com.voting.system.project.TestDataHelper.USER_ID_2;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2;
import static com.voting.system.project.TestDataHelper.getNewVote;
import static com.voting.system.project.TestDataHelper.getUpdatedVote;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.VoteTestUtil.checkSave;
import static com.voting.system.project.util.VoteTestUtil.checkUpdate;
import static org.junit.jupiter.api.Assertions.assertNull;

class VoteRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void save() {
        final Vote saved = voteRepository.save(getNewVote());
        checkSave(saved);
    }

    @Test
    void update() {
        final Vote updated = voteRepository.save(getUpdatedVote(VOTE_USER_2));
        checkUpdate(updated);
    }

    @Test
    void findVoteByUserIdOnCurrentDate() {
        final Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_2);
        assertMatch(actual, VOTE_USER_2);
    }

    @Test
    void findVoteByUserIdOnCurrentDateNotExist() {
        final Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_1);
        assertNull(actual);
    }
}