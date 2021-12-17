package com.mushrooms.gatewayservice.proxy;

import com.mushrooms.gatewayservice.model.MushroomReceiptDTO;
import com.mushrooms.gatewayservice.model.MushroomRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient("mushroom-service")
public interface MushroomProxy {

    @GetMapping("/api/mushrooms")
    @ResponseStatus(HttpStatus.OK)
    public List<MushroomReceiptDTO> findAllMushrooms();

    @GetMapping("/api/mushrooms/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomByMushroomName (@PathVariable(name="mushroomName") String mushroomName);

    @GetMapping("/api/mushrooms/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO findMushroomById (@PathVariable(name="id") Long id);

    @GetMapping("/api/mushrooms/containing/{mushroomName}")
    @ResponseStatus(HttpStatus.OK)
    public List<MushroomReceiptDTO> findByMushroomNameContaining (@PathVariable(name="mushroomName") String mushroomName);

    @PostMapping("/api/mushrooms/new")
    @ResponseStatus(HttpStatus.CREATED)
    public MushroomReceiptDTO createMushroom(@RequestBody @Valid MushroomRequestDTO mushroomRequestDTO);

    @PutMapping ("/api/mushrooms/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MushroomReceiptDTO updateMushroom(@PathVariable Long id, @RequestBody MushroomRequestDTO mushroomRequestDTO);

    @DeleteMapping("/api/mushrooms/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMushroom(@PathVariable Long id);
}
