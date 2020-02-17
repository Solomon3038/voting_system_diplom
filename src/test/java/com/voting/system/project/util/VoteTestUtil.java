package com.voting.system.project.util;

import com.voting.system.project.model.Vote;

import java.time.LocalTime;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.util.ValidationUtil.VOTE_MAX_TIME;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class VoteTestUtil {

    private VoteTestUtil() {
    }

    public static void checkSave(Vote saved) {
        Vote expected = getNewVote();
        expected.setId(saved.getId());
        assertMatch(saved, expected);
    }

    public static void checkUpdate(Vote updated) {
        Vote expected = getUpdatedVote(VOTE_USER_2);
        assertMatch(updated, expected);
    }

    public static void checkIfRunTest() {
        assumeTrue(LocalTime.now().compareTo(VOTE_MAX_TIME) <= 0,
                "text execution time is from 00:00:00AM till " + VOTE_MAX_TIME + "AM");
    }
}
