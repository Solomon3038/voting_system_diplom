package com.voting.system.project.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

//https://mkyong.com/spring-boot/spring-boot-how-to-init-a-bean-for-testing/
@TestConfiguration
class ServiceConfiguration {

    @Bean
    public RestaurantService restaurantServiceBean() {
        return new RestaurantService();
    }

    @Bean
    public MenuService menuServiceBean() {
        return new MenuService();
    }

    @Bean
    public VoteService voteServiceBean() {
        return new VoteService();
    }
}