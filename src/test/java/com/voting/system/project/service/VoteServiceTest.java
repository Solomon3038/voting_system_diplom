package com.voting.system.project.service;

import com.voting.system.project.AbstractTest;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.NotExistException;
import com.voting.system.project.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_DATE;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_3;
import static com.voting.system.project.TestDataHelper.USER_ID_1;
import static com.voting.system.project.TestDataHelper.USER_ID_2;
import static com.voting.system.project.TestDataHelper.VOTE_ID_1;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2_NOW;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteServiceTest extends AbstractTest {

    @Autowired
    private VoteService voteService;

    @Test
    void get() {
        final VoteTo actual = voteService.getOnDate(USER_ID_2, LocalDate.now());
        assertMatch(actual, mapper.map(VOTE_USER_2_NOW, VoteTo.class));
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistException.class, () -> voteService.getOnDate(USER_ID_2, NOT_EXIST_DATE));
    }

    @Test
    void save() {
        final VoteTo saved = voteService.create(USER_ID_1, RESTAURANT_ID_2, null);
        final VoteTo expected = new VoteTo(saved.getId(), LocalDate.now(), USER_ID_1, RESTAURANT_ID_2);
        assertMatch(saved, expected);
    }

    @Test
    void saveDateLessCurrentDate() {
        assertThrows(VoteException.class,
                () -> voteService.create(USER_ID_1, RESTAURANT_ID_2, LocalDate.of(2020, 1, 1)));
    }

    @Test
    void update() {
        voteService.update(USER_ID_2, RESTAURANT_ID_3, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
        final VoteTo updated = voteService.getOnDate(USER_ID_2, LocalDate.now());
        final VoteTo expected = new VoteTo(VOTE_ID_1, LocalDate.now(), USER_ID_2, RESTAURANT_ID_3);
        assertMatch(updated, expected);
    }

    @Test
    void updateNotInTime() {
        assertThrows(VoteException.class,
                () -> voteService.update(USER_ID_2, RESTAURANT_ID_3, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0, 1))));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistException.class,
                () -> voteService.update(USER_ID_2, RESTAURANT_ID_3, LocalDateTime.of(NOT_EXIST_DATE, LocalTime.of(10, 0))));
    }
}