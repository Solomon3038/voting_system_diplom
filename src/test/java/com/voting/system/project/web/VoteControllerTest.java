package com.voting.system.project.web;

import com.voting.system.project.service.VoteService;
import com.voting.system.project.to.VoteTo;
import com.voting.system.project.util.exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.voting.system.project.TestDataHelper.NOT_EXIST_DATE;
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
import static com.voting.system.project.TestDataHelper.VOTE_USER_1;
import static com.voting.system.project.TestDataHelper.VOTE_USER_2;
import static com.voting.system.project.util.TestMatcherUtil.assertMatch;
import static com.voting.system.project.web.RestaurantController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    public static final String VOTE_REST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_2 + "/votes/";
    public static final String VOTE_NOT_EXIST_REST_URL_TEST = REST_URL + "/" + RESTAURANT_ID_NEXT + "/votes/";
    public static final String DATE_PARAM = "?date=" + VOTE_USER_2.getDate();
    public static final String NOT_EXIST_DATE_PARAM = "?date=" + NOT_EXIST_DATE;

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void get() throws Exception {
        doGet(VOTE_REST_URL_TEST + DATE_PARAM, objectMapper.writeValueAsString(mapper.map(VOTE_USER_2, VoteTo.class)));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void getNotExist() throws Exception {
        doGetNotExist(VOTE_REST_URL_TEST + NOT_EXIST_DATE_PARAM);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    void createWithLocation() throws Exception {
        final String result = mockMvc.perform(post(VOTE_REST_URL_TEST)
                .contentType(MediaType.APPLICATION_JSON)
                .param("date", "2022-03-14"))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        final VoteTo created = objectMapper.readValue(result, VoteTo.class);
        final LocalDate date = LocalDate.of(2022, 3, 14);
        VoteTo expected = new VoteTo(created.getId(), date, USER_ID_1, RESTAURANT_ID_2);
        assertMatch(created, expected);
        assertMatch(voteService.getOnDate(USER_ID_1, date), expected);
    }

    @Test
    @WithUserDetails(USER_1_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createNotExist() throws Exception {
        doPostErr("", VOTE_NOT_EXIST_REST_URL_TEST, status().isConflict());
        assertThrows(NotExistException.class, () -> voteService.getOnDate(USER_ID_1, LocalDate.now()));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicateData() throws Exception {
        doPostErr("", VOTE_REST_URL_TEST, status().isConflict());
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void createDateLessCurrentDate() throws Exception {
        mockMvc.perform(post(VOTE_REST_URL_TEST)
                .contentType(MediaType.APPLICATION_JSON)
                .param("date", "2020-01-03"))
                .andDo(print())
                .andExpect(status().isRequestTimeout());
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void update() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPut(value, REST_URL + "/" + RESTAURANT_ID_3 + "/votes/");
        assertMatch(voteService.getOnDate(USER_ID_2, date.toLocalDate()), new VoteTo(VOTE_ID_1, date.toLocalDate(), USER_ID_2, RESTAURANT_ID_3));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void updateNotExist() throws Exception {
        final LocalDateTime date = LocalDateTime.of(NOT_EXIST_DATE, LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, REST_URL + "/" + RESTAURANT_ID_3 + "/votes/", status().isUnprocessableEntity());
        assertMatch(voteService.getOnDate(USER_ID_1, VOTE_USER_1.getDate()), new VoteTo(VOTE_ID_2, VOTE_USER_1.getDate(), USER_ID_1, RESTAURANT_ID_3));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateNotExistRestaurant() throws Exception {
        final LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, VOTE_NOT_EXIST_REST_URL_TEST, status().isConflict());
        assertMatch(voteService.getOnDate(USER_ID_2, LocalDate.now()), new VoteTo(VOTE_ID_1, LocalDate.now(), USER_ID_2, RESTAURANT_ID_1));
    }

    @Test
    @WithUserDetails(USER_2_EMAIL)
    void updateNotInTime() throws Exception {
        final LocalDateTime date = LocalDateTime.of(VOTE_USER_2.getDate(), LocalTime.of(11, 0, 1));
        final String value = objectMapper.writeValueAsString(date);
        doPutErr(value, VOTE_REST_URL_TEST, status().isRequestTimeout());
        assertMatch(voteService.getOnDate(USER_ID_2, date.toLocalDate()), new VoteTo(VOTE_ID_3, date.toLocalDate(), USER_ID_2, RESTAURANT_ID_2));
    }
}