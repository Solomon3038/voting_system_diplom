package com.voting.system.project.repository;

import com.voting.system.project.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findById(int id);

    @Query("SELECT r FROM Restaurant r ORDER BY r.name ASC")
    List<Restaurant> findAll();

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menus m WHERE m.registered >= current_date ORDER BY r.name ASC")
    List<Restaurant> findAllWithMenusOnCurrentDate();
}