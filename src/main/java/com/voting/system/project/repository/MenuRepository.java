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

    Menu findByIdAndRestaurantId(int id, int restaurantId);
}