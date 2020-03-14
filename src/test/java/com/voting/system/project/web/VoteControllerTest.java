package com.voting.system.project.web;

import com.voting.system.project.service.VoteService;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_ID;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_1;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_2;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_3;
import static com.voting.system.project.TestDataHelper.RESTAURANT_ID_NEXT;
import static com.voting.system.project.TestDataHelper.USER_1_EMAIL;
import static com.voting.system.project.TestDataHelper.USER_2_EMAIL;
import static com.voting.system.project.TestDataHelper.USER_ID_1;
import static com.voting.system.project.TestDataHelper.USER_ID_2;
import static com.voting.system.project.TestDataHelper.VOTE_ID_1;
import static com.voting.system.project.TestDataHelper.VOTE_ID_2;
import static com.voting.system.project.TestDataHelper.VOTE_ID_3;
import static com.voting.system.project.TestDataHelper.VOTE_ID_NEXT;
import static com.voting.system.project.TestDataHelper.VOTE_USER_1;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2_NOW;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.RestaurantController.REST_URL;
import static com.voting.system.project.web.VoteController.VOTE_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String VOTE_REST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_2 + "/votes/";
    public static final String VOTE_NOT_EXIST_REST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_NEXT + "/votes/";
    public static final String VOTE_URL_TEST = VOTE_URL + "/";
    public static final String DATE_PARAM = "?date=" + VOTE_USER_2.getDate();

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void getAll() throws Exception {
        doGet(VOTE_URL, objectMapper.writeValueAsString(mapper.mapAsList(List.of(VOTE_USER_2_NOW, VOTE_USER_2), VoteTo.class)));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void get() throws Exception {
        doGet(VOTE_URL_TEST + VOTE_ID_3 + DATE_PARAM, objectMapper.writeValueAsString(mapper.map(VOTE_USER_2, VoteTo.class)));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void getNotExist() throws Exception {
        doGetNotExist(VOTE_URL_TEST + NOT_EXIST_ID + DATE_PARAM);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void getNotOwn() throws Exception {
        doGetNotExist(VOTE_URL_TEST + VOTE_ID_3 + DATE_PARAM);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void createWithLocation() throws Exception {
        final LocalDate date = LocalDate.of(2020, 3, 14);
        final String value = objectMapper.writeValueAsString(date);
        final String result = doPost(value, VOTE_REST_URL_TEST);
        final VoteTo created = objectMapper.readValue(result, VoteTo.class);
        VoteTo expected = new VoteTo(created.getId(), date, USER_ID_1, RESTAURANT_ID_2);
        assertMatch(created, expected);
        assertMatch(voteService.get(created.getId(), USER_ID_1, date), expected);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createNotExist() throws Exception {
        doPostErr("", VOTE_NOT_EXIST_REST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> voteService.get(VOTE_ID_NEXT, USER_ID_1, LocalDate.now()));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        doPostErr("", VOTE_REST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> voteService.get(VOTE_ID_NEXT, USER_ID_2, LocalDate.now()));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void update() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPut(value, REST_URL + "/" + RESTAURANT_ID_3 + "/votes/" + VOTE_ID_1);
        assertMatch(voteService.get(VOTE_ID_1, USER_ID_2, date.toLocalDate()), new VoteTo(VOTE_ID_1, date.toLocalDate(), USER_ID_2, RESTAURANT_ID_3));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void updateNotExist() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, REST_URL + "/" + RESTAURANT_ID_3 + "/votes/" + VOTE_ID_2, status().isUnprocessableEntity());
        assertMatch(voteService.get(VOTE_ID_2, USER_ID_1, VOTE_USER_1.getDate()), new VoteTo(VOTE_ID_2, VOTE_USER_1.getDate(), USER_ID_1, RESTAURANT_ID_3));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateNotExistRestaurant() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, VOTE_NOT_EXIST_REST_URL_TEST + VOTE_ID_1, status().isConflict());
        assertMatch(voteService.get(VOTE_ID_1, USER_ID_2, LocalDate.now()), new VoteTo(VOTE_ID_1, LocalDate.now(), USER_ID_2, RESTAURANT_ID_1));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void updateNotOwn() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, VOTE_REST_URL_TEST + VOTE_ID_2, status().isUnprocessableEntity());
        assertMatch(voteService.get(VOTE_ID_2, USER_ID_1, VOTE_USER_1.getDate()), new VoteTo(VOTE_ID_2, VOTE_USER_1.getDate(), USER_ID_1, RESTAURANT_ID_3));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void updateNotInTime() throws Exception {
        final LocalDateTime date = LocalDateTime.of(VOTE_USER_2.getDate(), LocalTime.of(11, 0, 1));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, VOTE_REST_URL_TEST + VOTE_ID_3, status().isRequestTimeout());
        assertMatch(voteService.get(VOTE_ID_3, USER_ID_2, date.toLocalDate()), new VoteTo(VOTE_ID_3, date.toLocalDate(), USER_ID_2, RESTAURANT_ID_2));
    }
}