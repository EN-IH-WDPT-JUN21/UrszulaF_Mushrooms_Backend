package com.mushrooms.animalservice.controller;

import com.mushrooms.animalservice.dao.Animal;
import com.mushrooms.animalservice.dto.AnimalReceiptDTO;
import com.mushrooms.animalservice.dto.AnimalRequestDTO;
import com.mushrooms.animalservice.repository.AnimalRepository;
import com.mushrooms.animalservice.service.interfaces.IAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    IAnimalService animalService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Animal> findAllAnimals(){
        return animalService.getAllAnimals();
    }

    @GetMapping("/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalByAnimalName (@PathVariable(name="animalName") String animalName){
        return animalService.findByAnimalName(animalName);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalById (@PathVariable(name="id") Long id){
        return animalService.findById(id);
    }

    @GetMapping("/containing/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Animal> findByAnimalNameContaining (@PathVariable(name="animalName") String animalName){
        return animalService.findByAnimalNameContaining(animalName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalReceiptDTO createAnimal(@RequestBody @Valid AnimalRequestDTO animalRequestDTO){
        return animalService.createAnimal(animalRequestDTO);
    }

    @PutMapping ("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO updateAnimal(@PathVariable Long id, @RequestBody AnimalRequestDTO animalRequestDTO) {
        return animalService.updateAnimal(id, animalRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }
}
