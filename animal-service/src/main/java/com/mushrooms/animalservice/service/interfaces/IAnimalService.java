package com.mushrooms.animalservice.service.interfaces;

import com.mushrooms.animalservice.dao.Animal;
import com.mushrooms.animalservice.dto.AnimalReceiptDTO;
import com.mushrooms.animalservice.dto.AnimalRequestDTO;

import java.util.List;

public interface IAnimalService {
    List<Animal> getAllAnimals();
    AnimalReceiptDTO findByAnimalName (String animalName);
    AnimalReceiptDTO findById(Long id);
    List<Animal> findByAnimalNameContaining(String animalName);

    void deleteAnimal(Long id);

    AnimalReceiptDTO createAnimal(AnimalRequestDTO animalRequestDTO);

    AnimalReceiptDTO updateAnimal(Long id, AnimalRequestDTO animalRequestDTO);
}
