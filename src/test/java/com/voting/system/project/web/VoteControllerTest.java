package com.voting.system.project.web;

import com.voting.system.project.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.web.RestaurantController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    public static final String VOTE_URL_TEST = REST_URL + "/" + RESTAURANT_ID_2 + "/votes/";
    public static final String VOTE_NOT_EXIST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_NEXT + "/votes/";

    @Autowired
    private VoteRepository voteRepository;
}