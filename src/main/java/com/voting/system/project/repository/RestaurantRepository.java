package com.voting.system.project.repository;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findById(int id);

    @Query("SELECT r FROM Restaurant r ORDER BY r.name ASC")
    List<Restaurant> findAll();

    @Query("SELECT mi FROM MenuItem mi LEFT JOIN FETCH mi.dish d  LEFT JOIN FETCH d.restaurant r WHERE mi.date=:date")
    List<MenuItem> findAllOnDate(LocalDate date);
}