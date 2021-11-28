package com.mushrooms.mushroomservice.repository;

import com.mushrooms.mushroomservice.dao.Mushroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MushroomRepository extends JpaRepository<Mushroom, Long> {
    Optional<Mushroom> findByMushroomName(String mushroomName);
}
