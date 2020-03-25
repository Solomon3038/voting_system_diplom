package com.voting.system.project.repository;

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

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menuItems mi LEFT JOIN FETCH mi.dish d WHERE mi.date=:date ORDER BY r.name ASC, d.name ASC")
    List<Restaurant> findAllWithMenuItemsOnDate(LocalDate date);
}