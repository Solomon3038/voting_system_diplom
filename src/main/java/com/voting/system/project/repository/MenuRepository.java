package com.voting.system.project.repository;

import com.voting.system.project.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurantIdOrderByRegisteredDesc(int restaurantId);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.dishes d WHERE m.id=:id and m.restaurant.id=:restaurantId")
    Menu findByIdAndRestaurantId(int id, int restaurantId);
}