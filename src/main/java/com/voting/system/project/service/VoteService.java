package com.voting.system.project.service;

import com.voting.system.project.model.Restaurant;
import com.voting.system.project.model.User;
import com.voting.system.project.model.Vote;
import com.voting.system.project.repository.RestaurantRepository;
import com.voting.system.project.repository.UserRepository;
import com.voting.system.project.repository.VoteRepository;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.mapper.OrikaMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.voting.system.project.util.ValidationUtil.checkNotExistWithId;
import static com.voting.system.project.util.ValidationUtil.checkTime;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrikaMapper mapper;

    public VoteService(VoteRepository voteRepository,
                       UserRepository userRepository,
                       RestaurantRepository restaurantRepository,
                       OrikaMapper mapper) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public List<VoteTo> getAll(int userId) {
        return mapper.mapAsList(voteRepository.findAll(userId), VoteTo.class);
    }

    public VoteTo get(int id, int userId, LocalDate date) {
        Vote vote = checkNotExistWithId(voteRepository.findVoteById(id, userId, date), id);
        return mapper.map(vote, VoteTo.class);
    }

    @Transactional
    public VoteTo create(int userId, int restId, @Nullable LocalDate date) {
        final User user = userRepository.getOne(userId);
        final Restaurant restaurant = restaurantRepository.getOne(restId);
        Vote vote = voteRepository.save(new Vote(null, date, user, restaurant));
        return mapper.map(vote, VoteTo.class);
    }

    @Transactional
    public void update(int id, int userId, int restId, LocalDateTime dateTime) {
        checkTime(dateTime.toLocalTime());
        final LocalDate date = dateTime.toLocalDate();
        checkNotExistWithId(voteRepository.findVoteById(id, userId, date), id);
        final User user = userRepository.getOne(userId);
        final Restaurant restaurant = restaurantRepository.getOne(restId);
        voteRepository.save(new Vote(id, date, user, restaurant));
    }
}