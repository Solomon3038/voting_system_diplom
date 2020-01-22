package com.voting.system.project.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

//https://mkyong.com/spring-boot/spring-boot-how-to-init-a-bean-for-testing/
@TestConfiguration
class ServiceConfiguration {

    @Bean
    public UserService userServiceBean() {
        return new UserService();
    }

    @Bean
    public RestaurantService restaurantServiceBean() {
        return new RestaurantService();
    }
}