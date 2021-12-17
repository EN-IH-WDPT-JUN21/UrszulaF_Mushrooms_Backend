package com.mushrooms.gatewayservice.controller;

import com.mushrooms.gatewayservice.model.AnimalReceiptDTO;
import com.mushrooms.gatewayservice.model.AnimalRequestDTO;
import com.mushrooms.gatewayservice.proxy.AnimalProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/animals")
public class AnimalProxyController {
    @Autowired
    private AnimalProxy animalProxy;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalReceiptDTO> findAllAnimals(){
        return animalProxy.findAllAnimals();
    }

    @GetMapping("/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalByAnimalName (@PathVariable(name="animalName") String animalName){
        return animalProxy.findAnimalByAnimalName(animalName);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalById (@PathVariable(name="id") Long id){
        return animalProxy.findAnimalById(id);
    }

    @GetMapping("/containing/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalReceiptDTO> findByAnimalNameContaining (@PathVariable(name="animalName") String animalName){
        return animalProxy.findByAnimalNameContaining(animalName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalReceiptDTO createAnimal(@RequestBody @Valid AnimalRequestDTO animalRequestDTO){
        return animalProxy.createAnimal(animalRequestDTO);
    }

    @PutMapping ("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO updateAnimal(@PathVariable Long id, @RequestBody AnimalRequestDTO animalRequestDTO) {
        return animalProxy.updateAnimal(id, animalRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimal(@PathVariable Long id) {
        animalProxy.deleteAnimal(id);
    }
}
