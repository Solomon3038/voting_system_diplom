package com.voting.system.project.util;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.to.VoteTo;

public class VoteUtil {

    public static Vote getFromTo(VoteTo voteTo, User user, Restaurant restaurant) {
        return new Vote(voteTo.getId(), voteTo.getDate(), restaurant, user);
    }

    public static VoteTo getToFrom(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }
}
