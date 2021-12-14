package com.mushrooms.gatewayservice.repository;

import com.mushrooms.gatewayservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    Optional<User> findOptionalByUsername(String username);
    List<User> findByUsernameContaining(String username);
}

