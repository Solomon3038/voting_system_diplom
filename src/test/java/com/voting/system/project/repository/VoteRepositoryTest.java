package com.voting.system.project.repository;

import com.voting.system.project.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class VoteRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void save() {
        Vote saved = voteRepository.save(getNewVote());
        Vote expected = getNewVote();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    @Test
    void update() {
        Vote updated = voteRepository.save(getUpdatedVote(VOTE_USER_2));
        Vote expected = getUpdatedVote(VOTE_USER_2);
        assertMatch(updated, expected);
    }
}