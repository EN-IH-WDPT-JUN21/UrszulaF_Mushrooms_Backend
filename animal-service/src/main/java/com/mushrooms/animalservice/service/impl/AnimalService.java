package com.mushrooms.animalservice.service.impl;

import com.mushrooms.animalservice.dao.Animal;
import com.mushrooms.animalservice.dto.AnimalReceiptDTO;
import com.mushrooms.animalservice.dto.AnimalRequestDTO;
import com.mushrooms.animalservice.repository.AnimalRepository;
import com.mushrooms.animalservice.service.interfaces.IAnimalService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService implements IAnimalService {
    @Autowired
    AnimalRepository animalRepository;


    Logger logger = LoggerFactory.getLogger("AnimalService.class");

    @Retry(name = "animal-api", fallbackMethod = "fallbackAnimalList")
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    //    @Retry(name = "animal-api", fallbackMethod = "fallbackAnimalDTO")
    public AnimalReceiptDTO findByAnimalName(String animalName) {
        Optional<Animal> optionalAnimal = animalRepository.findByAnimalName(animalName.toLowerCase().trim());

        if(optionalAnimal.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal name " + animalName + " not found!");
        }

        Animal animal= optionalAnimal.isPresent()? optionalAnimal.get() : null;
        return convertAnimalToDTO(animal);
    }

    public AnimalReceiptDTO findById(Long id) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);

        if(optionalAnimal.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal with id " + id + " not found!");
        }

        Animal animal= optionalAnimal.isPresent()? optionalAnimal.get() : null;
        return convertAnimalToDTO(animal);
    }

    public List<Animal> findByAnimalNameContaining(String animalName) {
        List<Animal> optionalAnimal = animalRepository.findByAnimalNameContaining(animalName.toLowerCase().trim());

        if(optionalAnimal.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal name " + animalName + " not found!");
        }

        return optionalAnimal;
    }

    //    @Retry(name = "animal-api", fallbackMethod = "fallbackAnimalDTO")
    public AnimalReceiptDTO createAnimal(AnimalRequestDTO animalRequestDTO) {
        Optional<Animal> optionalAnimal = animalRepository.findByAnimalName(animalRequestDTO.getAnimalName().toLowerCase().trim());

        if(optionalAnimal.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal name " + animalRequestDTO.getAnimalName() + " already exist!");
        }
        try{
            Animal animal;
            animal = convertDTOToAnimal(animalRequestDTO);
            animalRepository.save(animal);
            return convertAnimalToDTO(animal);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal type value not valid.");
        }
    }

    //    @Retry(name = "animal-api", fallbackMethod = "fallbackNull")
    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal id " + id + " not found!"));
        animalRepository.delete(animal);
    }

    //    @Retry(name = "animal-api", fallbackMethod = "fallbackAnimalDTO")
    public AnimalReceiptDTO updateAnimal(Long id, AnimalRequestDTO animalRequestDTO) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal id " + id + " not found!"));
        if (animalRequestDTO.getAnimalName() != null && animalRequestDTO.getAnimalName().toLowerCase().trim() != "") {
            if(!animalRequestDTO.getAnimalName().equals(animal.getAnimalName())){
                Optional<Animal> optionalAnimal = animalRepository.findByAnimalName(animalRequestDTO.getAnimalName().toLowerCase().trim());

                if(optionalAnimal.isPresent()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal name " + animalRequestDTO.getAnimalName() + " already exist!");
                }
            }
            animal.setAnimalName(animalRequestDTO.getAnimalName().toLowerCase().trim());
        }
        if (animalRequestDTO.getOtherNames() != null && animalRequestDTO.getOtherNames() != "") {
            animal.setOtherNames(animalRequestDTO.getOtherNames());
        }
        if (animalRequestDTO.getPhotoURL() != null && animalRequestDTO.getPhotoURL() != "") {
            animal.setPhotoURL(animalRequestDTO.getPhotoURL());
        }
        if (animalRequestDTO.getAnimalType() != null && animalRequestDTO.getAnimalType() != "") {
            animal.setAnimalType(animalRequestDTO.getAnimalType());
        }
        if (animalRequestDTO.getAnimalSize() != null && animalRequestDTO.getAnimalSize() != "") {
            animal.setAnimalSize(animalRequestDTO.getAnimalSize());
        }
        if (animalRequestDTO.getDescription() != null && animalRequestDTO.getDescription() != "") {
            animal.setDescription(animalRequestDTO.getDescription());
        }
        if (animalRequestDTO.getRemarks() != null && animalRequestDTO.getRemarks() != "") {
            animal.setRemarks(animalRequestDTO.getRemarks());
        }

        animalRepository.save(animal);
        return convertAnimalToDTO(animal);
    }

    private AnimalReceiptDTO convertAnimalToDTO(Animal animal) {
        return new AnimalReceiptDTO(animal.getId(),
                animal.getPhotoURL(),
                animal.getAnimalName(),
                animal.getOtherNames(),
                animal.getAnimalType(),
                animal.getAnimalSize(),
                animal.getDescription(),
                animal.getRemarks()
        );
    }

    private Animal convertDTOToAnimal(AnimalRequestDTO animalRequestDTO) {
        return new Animal(
                animalRequestDTO.getPhotoURL(),
                animalRequestDTO.getAnimalName().toLowerCase().trim(),
                animalRequestDTO.getOtherNames(),
                animalRequestDTO.getAnimalType(),
                animalRequestDTO.getAnimalSize(),
                animalRequestDTO.getDescription(),
                animalRequestDTO.getRemarks()
        );
    }

    //fallback

    public List<Animal>  fallbackAnimalList(Exception e) {
        logger.info("call animal fallback method");
        List<Animal> fallbackAnimalList = new ArrayList<>();
        Animal fallbackAnimal = new Animal("", "fallback", "", "","","","");
        fallbackAnimalList.add(fallbackAnimal);
        return fallbackAnimalList;
    }

    public Animal fallbackAnimal(Exception e) {
        logger.info("call animal fallback method");
        Animal fallbackAnimal = new Animal("", "fallback", "", "","","","");
        return fallbackAnimal;
    }

    public AnimalReceiptDTO  fallbackAnimalDTO(Exception e) {
        logger.info("call animal fallback method");
        AnimalReceiptDTO  fallbackAnimal = new AnimalReceiptDTO (1000l, "", "fallback", "", "","","","");
        return fallbackAnimal;
    }

    public void fallbackNull(Exception e) {
        logger.info("call animal fallback method");
    }
}