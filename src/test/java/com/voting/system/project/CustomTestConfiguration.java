package com.voting.system.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.system.project.service.DishService;
import com.voting.system.project.service.MenuService;
import com.voting.system.project.service.RestaurantService;
import com.voting.system.project.service.VoteService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

//https://mkyong.com/spring-boot/spring-boot-how-to-init-a-bean-for-testing/
@TestConfiguration
public class CustomTestConfiguration {

    @Bean
    public RestaurantService restaurantServiceBean() {
        return new RestaurantService();
    }

    @Bean
    public MenuService menuServiceBean() {
        return new MenuService();
    }

    @Bean
    public DishService dishServiceBean() {
        return new DishService();
    }

    @Bean
    public VoteService voteServiceBean() {
        return new VoteService();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}