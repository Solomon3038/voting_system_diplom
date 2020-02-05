package com.voting.system.project.util;

import com.voting.system.project.model.Vote;

import static com.voting.system.project.TestData.*;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;

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
}
