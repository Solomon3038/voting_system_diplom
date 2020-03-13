package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.to.VoteTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_3;
import static com.voting.system.project.TestDataHelper.USER_ID_1;
import static com.voting.system.project.TestDataHelper.USER_ID_2;
import static com.voting.system.project.TestDataHelper.VOTE_ID_1;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

class VoteServiceTest extends AbstractTest {

    @Autowired
    private VoteService voteService;

    @Test
    void get() {
        final VoteTo actual = voteService.get(VOTE_ID_1, USER_ID_2, LocalDate.now());
        assertMatch(actual, mapper.map(VOTE_USER_2, VoteTo.class));
    }

    @Test
    void save() {
        final VoteTo saved = voteService.create(USER_ID_1, RESTAURANT_ID_2, null);
        final VoteTo expected = new VoteTo(saved.getId(), LocalDate.now(), USER_ID_1, RESTAURANT_ID_2);
        assertMatch(saved, expected);
    }

    @Test
    void update() {
        voteService.update(VOTE_ID_1, USER_ID_2, RESTAURANT_ID_3, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        VoteTo updated = voteService.get(VOTE_ID_1, USER_ID_2, LocalDate.now());
        VoteTo expected = new VoteTo(VOTE_ID_1, LocalDate.now(), USER_ID_2, RESTAURANT_ID_3);
        assertMatch(updated, expected);
    }
}