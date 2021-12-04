package com.mushrooms.mushroomservice.service.interfaces;

import com.mushrooms.mushroomservice.dao.Mushroom;
import com.mushrooms.mushroomservice.dto.MushroomReceiptDTO;
import com.mushrooms.mushroomservice.dto.MushroomRequestDTO;

import java.util.List;

public interface IMushroomService {
    List<Mushroom> getAllMushrooms();
    MushroomReceiptDTO findByMushroomName (String mushroomName);
    List<Mushroom> findByMushroomNameContaining(String mushroomName);

    void deleteMushroom(String mushroomName);

    MushroomReceiptDTO createMushroom(MushroomRequestDTO mushroomRequestDTO);

    MushroomReceiptDTO updateMushroom(String mushroomName, MushroomRequestDTO mushroomRequestDTO);
}
