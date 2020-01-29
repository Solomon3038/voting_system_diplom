package com.voting.system.project.repository;

import com.voting.system.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id=:id")
    User findUserById(int id);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.name=:name")
    User findUserByEmail(String name);
}