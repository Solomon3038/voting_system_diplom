package com.voting.system.project.web;

import com.voting.system.project.model.Vote;
import com.voting.system.project.repository.VoteRepository;
import com.voting.system.project.to.VoteTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.VoteTestUtil.checkIfRunTest;
import static com.voting.system.project.util.VoteUtil.getToFrom;
import static com.voting.system.project.web.RestaurantController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    public static final String VOTE_URL_TEST = REST_URL + "/" + RESTAURANT_ID_2 + "/votes/";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void create() throws Exception {
        checkIfRunTest();
        doPut("", VOTE_URL_TEST);
        final Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_1);
        final VoteTo expected = new VoteTo(actual.getId(), USER_ID_1, RESTAURANT_ID_2);
        assertMatch(getToFrom(actual), expected);
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void update() throws Exception {
        checkIfRunTest();
        doPut("", VOTE_URL_TEST);
        final Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_2);
        final VoteTo expected = new VoteTo(actual.getId(), USER_ID_2, RESTAURANT_ID_2);
        assertMatch(getToFrom(actual), expected);
    }
}