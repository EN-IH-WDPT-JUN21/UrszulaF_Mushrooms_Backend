package com.mushrooms.animalservice.repository;

import com.mushrooms.animalservice.dao.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findByAnimalName(String animalName);
    List<Animal> findByAnimalNameContaining(String animalName);
}
