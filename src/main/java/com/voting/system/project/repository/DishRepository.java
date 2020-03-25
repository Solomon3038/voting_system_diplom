package com.voting.system.project.repository;

import com.voting.system.project.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId ORDER BY d.name ASC")
    List<Dish> findAllByRestaurantId(int restId);

    Dish findDishByIdAndRestaurantId(int id, int restId);
}