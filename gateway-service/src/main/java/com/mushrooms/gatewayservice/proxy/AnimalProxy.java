package com.mushrooms.gatewayservice.proxy;

import com.mushrooms.gatewayservice.model.AnimalReceiptDTO;
import com.mushrooms.gatewayservice.model.AnimalRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient("animal-service")
public interface AnimalProxy {

    @GetMapping("/api/animals")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalReceiptDTO> findAllAnimals();

    @GetMapping("/api/animals/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalByAnimalName (@PathVariable(name="animalName") String animalName);

    @GetMapping("/api/animals/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO findAnimalById (@PathVariable(name="id") Long id);

    @GetMapping("/api/animals/containing/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalReceiptDTO> findByAnimalNameContaining (@PathVariable(name="animalName") String animalName);

    @PostMapping("/api/animals/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalReceiptDTO createAnimal(@RequestBody @Valid AnimalRequestDTO animalRequestDTO);

    @PutMapping ("/api/animals/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AnimalReceiptDTO updateAnimal(@PathVariable Long id, @RequestBody AnimalRequestDTO animalRequestDTO);

    @DeleteMapping("/api/animals/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimal(@PathVariable Long id);
}
