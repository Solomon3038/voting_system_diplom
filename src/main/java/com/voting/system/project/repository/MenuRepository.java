package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurantIdOrderByRegisteredDesc(int restaurantId);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes d WHERE m.id=:id and m.restaurant.id=:restaurantId")
    Menu findByIdAndRestaurantId(int id, int restaurantId);

    @Transactional
    @Modifying
    @Query("UPDATE Menu m set m.registered=:registered WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int setValue(int id, int restaurantId, LocalDate registered);
}