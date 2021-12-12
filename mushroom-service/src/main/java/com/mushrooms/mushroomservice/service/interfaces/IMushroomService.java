package com.mushrooms.mushroomservice.service.interfaces;

import com.mushrooms.mushroomservice.dao.Mushroom;
import com.mushrooms.mushroomservice.dto.MushroomReceiptDTO;
import com.mushrooms.mushroomservice.dto.MushroomRequestDTO;

import java.util.List;

public interface IMushroomService {
    List<Mushroom> getAllMushrooms();
    MushroomReceiptDTO findByMushroomName (String mushroomName);
    MushroomReceiptDTO findById(Long id);
    List<Mushroom> findByMushroomNameContaining(String mushroomName);

    void deleteMushroom(Long id);

    MushroomReceiptDTO createMushroom(MushroomRequestDTO mushroomRequestDTO);

    MushroomReceiptDTO updateMushroom(Long id, MushroomRequestDTO mushroomRequestDTO);
}
