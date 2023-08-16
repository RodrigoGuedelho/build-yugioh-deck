package com.guedelho.buildYourDeck.repository;

import com.guedelho.buildYourDeck.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u from User u where u.login = :login ")
    public User findByLogin(@Param("login") String login);
}
