package com.voting.system.project.service;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.repository.UserRepository;
import com.voting.system.project.repository.VoteRepository;
import com.voting.system.project.to.VoteTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.voting.system.project.util.ValidationUtil.*;
import static com.voting.system.project.util.VoteUtil.getFromTo;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    //voteTo id value always null
    @Transactional
    public Vote createOrUpdate(VoteTo voteTo) {
        Assert.notNull(voteTo, "vote must not be null");
        checkDate(voteTo.getDate());
        checkTime();
        Restaurant restaurant = checkNotExistWithId(restaurantRepository.findById(voteTo.getRestaurantId().intValue()), voteTo.getRestaurantId());
        User user = userRepository.getOne(voteTo.getUserId());
        Vote existed = voteRepository.findVoteByUserIdOnCurrentDate(voteTo.getUserId());
        if (existed != null) {
            voteTo.setId(existed.getId());
        }
        Vote vote = getFromTo(voteTo, user, restaurant);
        return voteRepository.save(vote);
    }
}