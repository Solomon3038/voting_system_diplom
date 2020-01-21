package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import com.voting.system.project.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId ORDER BY m.registered DESC")
    List<Menu> findAllByRestaurantId(int restaurantId);

    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.dishes d WHERE m.restaurant.id=:restaurantId ORDER BY m.registered DESC")
    List<Menu> findAllByRestaurantIdWithDishes(int restaurantId);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes d WHERE m.id=:id")
    Menu findByIdWithDishes(int id);
}