package com.mushrooms.gatewayservice.controller;

import com.mushrooms.gatewayservice.model.MushroomReceiptDTO;
import com.mushrooms.gatewayservice.model.MushroomRequestDTO;
import com.mushrooms.gatewayservice.proxy.EventProxy;
import com.mushrooms.gatewayservice.proxy.MushroomProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mushrooms")
public class MushroomProxyController {

    @Autowired
    private MushroomProxy mushroomProxy;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MushroomReceiptDTO> findAllMushrooms(){
        return mushroomProxy.findAllMushrooms();
    }

    @GetMapping("/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomByMushroomName (@PathVariable(name="mushroomName") String mushroomName){
        return mushroomProxy.findMushroomByMushroomName(mushroomName);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomById (@PathVariable(name="id") Long id){
        return mushroomProxy.findMushroomById(id);
    }

    @GetMapping("/containing/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public List<MushroomReceiptDTO> findByMushroomNameContaining (@PathVariable(name="mushroomName") String mushroomName){
        return mushroomProxy.findByMushroomNameContaining(mushroomName);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public MushroomReceiptDTO createMushroom(@RequestBody @Valid MushroomRequestDTO mushroomRequestDTO){
        return mushroomProxy.createMushroom(mushroomRequestDTO);
    }

    @PutMapping ("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO updateMushroom(@PathVariable Long id, @RequestBody MushroomRequestDTO mushroomRequestDTO) {
        return mushroomProxy.updateMushroom(id, mushroomRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMushroom(@PathVariable Long id) {
        mushroomProxy.deleteMushroom(id);
    }
}
