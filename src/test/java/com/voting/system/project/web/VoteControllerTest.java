package com.voting.system.project.web;

import com.voting.system.project.model.Vote;
import com.voting.system.project.repository.VoteRepository;
import com.voting.system.project.to.VoteTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.TestDataHelper.USER_1_EMAIL;
import static com.voting.system.project.TestDataHelper.USER_2_EMAIL;
import static com.voting.system.project.TestDataHelper.USER_ID_1;
import static com.voting.system.project.TestDataHelper.USER_ID_2;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.VoteTestUtil.checkIfAfterTime;
import static com.voting.system.project.util.VoteTestUtil.checkIfBeforeTime;
import static com.voting.system.project.util.VoteUtil.getToFrom;
import static com.voting.system.project.web.RestaurantController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String VOTE_URL_TEST = REST_URL + "/" + RESTAURANT_ID_2 + "/votes/";
    public static final String VOTE_NOT_EXIST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_NEXT + "/votes/";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void create() throws Exception {
        doPut(USER_ID_1);
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void update() throws Exception {
        doPut(USER_ID_2);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createOrUpdateNotExist() throws Exception {
        checkIfBeforeTime();
        doPutErr("", VOTE_NOT_EXIST_URL_TEST, status().isConflict());
        assertNull(voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_1));
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void createOrUpdateNotInTime() throws Exception {
        checkIfAfterTime();
        doPutErr("", VOTE_URL_TEST, status().isRequestTimeout());
        assertNull(voteRepository.findVoteByUserIdOnCurrentDate(USER_ID_1));
    }

    private void doPut(int userId) throws Exception {
        checkIfBeforeTime();
        doPut("", VOTE_URL_TEST);
        final Vote actual = voteRepository.findVoteByUserIdOnCurrentDate(userId);
        final VoteTo expected = new VoteTo(actual.getId(), userId, RESTAURANT_ID_2);
        assertMatch(getToFrom(actual), expected);
    }
}