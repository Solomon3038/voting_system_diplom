package com.voting.system.project.service;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.repository.UserRepository;
import com.voting.system.project.repository.VoteRepository;
import com.voting.system.project.to.VoteTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.voting.system.project.util.ValidationUtil.checkDate;
import static com.voting.system.project.util.ValidationUtil.checkTime;
import static com.voting.system.project.util.VoteUtil.getFromTo;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Vote createOrUpdate(VoteTo voteTo) {
        Assert.notNull(voteTo, "vote must not be null");
        checkDate(voteTo.getDate());
        checkTime();
        final Restaurant restaurant = restaurantRepository.getOne(voteTo.getRestaurantId());
        final User user = userRepository.getOne(voteTo.getUserId());
        final Vote existed = voteRepository.findVoteByUserIdOnCurrentDate(voteTo.getUserId());
        if (existed != null) {
            voteTo.setId(existed.getId());
        }
        final Vote vote = getFromTo(voteTo, user, restaurant);
        return voteRepository.save(vote);
    }
}