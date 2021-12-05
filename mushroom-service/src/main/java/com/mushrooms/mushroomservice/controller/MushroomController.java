package com.mushrooms.mushroomservice.controller;

import com.mushrooms.mushroomservice.dao.Mushroom;
import com.mushrooms.mushroomservice.dto.MushroomReceiptDTO;
import com.mushrooms.mushroomservice.dto.MushroomRequestDTO;
import com.mushrooms.mushroomservice.repository.MushroomRepository;
import com.mushrooms.mushroomservice.service.interfaces.IMushroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mushrooms")
public class MushroomController {

    @Autowired
    MushroomRepository mushroomRepository;

    @Autowired
    IMushroomService mushroomService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Mushroom> findAllMushrooms(){
        return mushroomService.getAllMushrooms();
    }

    @GetMapping("/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomByMushroomName (@PathVariable(name="mushroomName") String mushroomName){
        return mushroomService.findByMushroomName(mushroomName);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomById (@PathVariable(name="id") Long id){
        return mushroomService.findById(id);
    }

    @GetMapping("/containing/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Mushroom> findByMushroomNameContaining (@PathVariable(name="mushroomName") String mushroomName){
        return mushroomService.findByMushroomNameContaining(mushroomName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public MushroomReceiptDTO createMushroom(@RequestBody @Valid MushroomRequestDTO mushroomRequestDTO){
        return mushroomService.createMushroom(mushroomRequestDTO);
    }

    @PutMapping ("/update/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO updateMushroom(@PathVariable String mushroomName, @RequestBody MushroomRequestDTO mushroomRequestDTO) {
        return mushroomService.updateMushroom(mushroomName, mushroomRequestDTO);
    }

    @DeleteMapping("/delete/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMushroom(@PathVariable String mushroomName) {
        mushroomService.deleteMushroom(mushroomName);
    }
}
