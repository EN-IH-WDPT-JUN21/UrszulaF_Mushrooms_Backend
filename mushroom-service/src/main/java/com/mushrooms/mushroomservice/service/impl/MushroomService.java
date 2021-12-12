package com.mushrooms.mushroomservice.service.impl;

import com.github.javafaker.Animal;
import com.mushrooms.mushroomservice.dao.Mushroom;
import com.mushrooms.mushroomservice.dto.MushroomReceiptDTO;
import com.mushrooms.mushroomservice.dto.MushroomRequestDTO;
import com.mushrooms.mushroomservice.enums.Consumable;
import com.mushrooms.mushroomservice.repository.MushroomRepository;
import com.mushrooms.mushroomservice.service.interfaces.IMushroomService;
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
public class MushroomService implements IMushroomService {
    @Autowired
    MushroomRepository mushroomRepository;


    Logger logger = LoggerFactory.getLogger("MushroomService.class");

    @Retry(name = "mushroom-api", fallbackMethod = "fallbackMushroomList")
    public List<Mushroom> getAllMushrooms() {
        return mushroomRepository.findAll();
    }

//    @Retry(name = "mushroom-api", fallbackMethod = "fallbackMushroomDTO")
    public MushroomReceiptDTO findByMushroomName(String mushroomName) {
        Optional<Mushroom> optionalMushroom = mushroomRepository.findByMushroomName(mushroomName.toLowerCase().trim());

        if(optionalMushroom.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MushroomName " + mushroomName + " not found!");
        }

        Mushroom mushroom= optionalMushroom.isPresent()? optionalMushroom.get() : null;
        return convertMushroomToDTO(mushroom);
    }

    public MushroomReceiptDTO findById(Long id) {
        Optional<Mushroom> optionalMushroom = mushroomRepository.findById(id);

        if(optionalMushroom.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mushroom with id " + id + " not found!");
        }

        Mushroom mushroom= optionalMushroom.isPresent()? optionalMushroom.get() : null;
        return convertMushroomToDTO(mushroom);
    }

    public List<Mushroom> findByMushroomNameContaining(String mushroomName) {
        List<Mushroom> optionalMushroom = mushroomRepository.findByMushroomNameContaining(mushroomName.toLowerCase().trim());

        if(optionalMushroom.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MushroomName " + mushroomName + " not found!");
        }

        return optionalMushroom;
    }

//    @Retry(name = "mushroom-api", fallbackMethod = "fallbackMushroomDTO")
    public MushroomReceiptDTO createMushroom(MushroomRequestDTO mushroomRequestDTO) {
        Optional<Mushroom> optionalMushroom = mushroomRepository.findByMushroomName(mushroomRequestDTO.getMushroomName().toLowerCase().trim());

        if(optionalMushroom.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mushroomname " + mushroomRequestDTO.getMushroomName() + " already exist!");
        }
        try{
            Mushroom mushroom;
            mushroom = convertDTOToMushroom(mushroomRequestDTO);
            mushroomRepository.save(mushroom);
            return convertMushroomToDTO(mushroom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mushroom type value not valid.");
        }
    }

//    @Retry(name = "mushroom-api", fallbackMethod = "fallbackNull")
    public void deleteMushroom(Long id) {
        Mushroom mushroom = mushroomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mushroom id " + id + " not found!"));
        mushroomRepository.delete(mushroom);
    }

//    @Retry(name = "mushroom-api", fallbackMethod = "fallbackMushroomDTO")
    public MushroomReceiptDTO updateMushroom(Long id, MushroomRequestDTO mushroomRequestDTO) {
        Mushroom mushroom = mushroomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mushroom id " + id + " not found!"));
        if (mushroomRequestDTO.getMushroomName() != null && mushroomRequestDTO.getMushroomName().toLowerCase().trim() != "") {
            if(!mushroomRequestDTO.getMushroomName().equals(mushroom.getMushroomName())){
                Optional<Mushroom> optionalMushroom = mushroomRepository.findByMushroomName(mushroomRequestDTO.getMushroomName().toLowerCase().trim());

                if(optionalMushroom.isPresent()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mushroom name " + mushroomRequestDTO.getMushroomName() + " already exist!");
                }
            }
            mushroom.setMushroomName(mushroomRequestDTO.getMushroomName().toLowerCase().trim());
        }
        if (mushroomRequestDTO.getOtherNames() != null && mushroomRequestDTO.getOtherNames() != "") {
            mushroom.setOtherNames(mushroomRequestDTO.getOtherNames());
        }
        if (mushroomRequestDTO.getPhotoURL() != null && mushroomRequestDTO.getPhotoURL() != "") {
            mushroom.setPhotoURL(mushroomRequestDTO.getPhotoURL());
        }
        if (mushroomRequestDTO.getEdible() != null) {
            mushroom.setEdible(mushroomRequestDTO.getEdible());
        }
        if (mushroomRequestDTO.getConsumableName() != null && mushroomRequestDTO.getConsumableName() != "") {
            mushroom.setConsumable(mushroomRequestDTO.getConsumable());
        }
        if (mushroomRequestDTO.getWhenFruiting() != null && mushroomRequestDTO.getWhenFruiting() != "") {
            mushroom.setWhenFruiting(mushroomRequestDTO.getWhenFruiting());
        }
        if (mushroomRequestDTO.getWhereFruiting() != null && mushroomRequestDTO.getWhereFruiting() != "") {
            mushroom.setWhereFruiting(mushroomRequestDTO.getWhereFruiting());
        }
        if (mushroomRequestDTO.getHat() != null && mushroomRequestDTO.getHat() != "") {
            mushroom.setHat(mushroomRequestDTO.getHat());
        }
        if (mushroomRequestDTO.getStem() != null && mushroomRequestDTO.getStem() != "") {
            mushroom.setStem(mushroomRequestDTO.getStem());
        }
        if (mushroomRequestDTO.getRing() != null && mushroomRequestDTO.getRing() != "") {
            mushroom.setRing(mushroomRequestDTO.getRing());
        }
        if (mushroomRequestDTO.getGills() != null && mushroomRequestDTO.getGills() != "") {
            mushroom.setGills(mushroomRequestDTO.getGills());
        }
        if (mushroomRequestDTO.getTubes() != null && mushroomRequestDTO.getTubes() != "") {
            mushroom.setTubes(mushroomRequestDTO.getTubes());
        }
        if (mushroomRequestDTO.getPulp() != null && mushroomRequestDTO.getPulp() != "") {
            mushroom.setPulp(mushroomRequestDTO.getPulp());
        }
        if (mushroomRequestDTO.getSmell() != null && mushroomRequestDTO.getSmell() != "") {
            mushroom.setSmell(mushroomRequestDTO.getSmell());
        }
        if (mushroomRequestDTO.getTaste() != null && mushroomRequestDTO.getTaste() != "") {
            mushroom.setTaste(mushroomRequestDTO.getTaste());
        }
        if (mushroomRequestDTO.getDifferentiation() != null && mushroomRequestDTO.getDifferentiation() != "") {
            mushroom.setDifferentiation(mushroomRequestDTO.getDifferentiation());
        }
        if (mushroomRequestDTO.getSimilar() != null && mushroomRequestDTO.getSimilar() != "") {
            mushroom.setSimilar(mushroomRequestDTO.getSimilar());
        }
        if (mushroomRequestDTO.getRemarks() != null && mushroomRequestDTO.getRemarks() != "") {
            mushroom.setRemarks(mushroomRequestDTO.getRemarks());
        }
        if (mushroomRequestDTO.getFoodValue() != null && mushroomRequestDTO.getFoodValue() != "") {
            mushroom.setFoodValue(mushroomRequestDTO.getFoodValue());
        }
        mushroomRepository.save(mushroom);
        return convertMushroomToDTO(mushroom);
    }

    private MushroomReceiptDTO convertMushroomToDTO(Mushroom mushroom) {
        return new MushroomReceiptDTO(mushroom.getId(),
                mushroom.getPhotoURL(),
                mushroom.getMushroomName(),
                mushroom.getOtherNames(),
                mushroom.getEdible(),
                mushroom.getConsumable(),
                mushroom.getWhenFruiting(),
                mushroom.getWhereFruiting(),
                mushroom.getHat(),
                mushroom.getStem(),
                mushroom.getRing(),
                mushroom.getGills(),
                mushroom.getTubes(),
                mushroom.getPulp(),
                mushroom.getSmell(),
                mushroom.getTaste(),
                mushroom.getDifferentiation(),
                mushroom.getSimilar(),
                mushroom.getRemarks(),
                mushroom.getFoodValue()
                );
    }

    private Mushroom convertDTOToMushroom(MushroomRequestDTO mushroomRequestDTO) {
        return new Mushroom(
                mushroomRequestDTO.getPhotoURL(),
                mushroomRequestDTO.getMushroomName().toLowerCase().trim(),
                mushroomRequestDTO.getOtherNames(),
                mushroomRequestDTO.getEdible(),
                mushroomRequestDTO.getConsumable(),
                mushroomRequestDTO.getWhenFruiting(),
                mushroomRequestDTO.getWhereFruiting(),
                mushroomRequestDTO.getHat(),
                mushroomRequestDTO.getStem(),
                mushroomRequestDTO.getRing(),
                mushroomRequestDTO.getGills(),
                mushroomRequestDTO.getTubes(),
                mushroomRequestDTO.getPulp(),
                mushroomRequestDTO.getSmell(),
                mushroomRequestDTO.getTaste(),
                mushroomRequestDTO.getDifferentiation(),
                mushroomRequestDTO.getSimilar(),
                mushroomRequestDTO.getRemarks(),
                mushroomRequestDTO.getFoodValue()
        );
    }

    //fallback

    public List<Mushroom>  fallbackMushroomList(Exception e) {
        logger.info("call mushroom fallback method");
        List<Mushroom> fallbackMushroomList = new ArrayList<>();
        Mushroom fallbackMushroom = new Mushroom("", "fallback", "", false, Consumable.POISONOUS,"","","","","","","","","","","","","","");
        fallbackMushroomList.add(fallbackMushroom);
        return fallbackMushroomList;
    }

    public Mushroom fallbackMushroom(Exception e) {
        logger.info("call mushroom fallback method");
        Mushroom fallbackMushroom = new Mushroom("", "fallback", "", false, Consumable.POISONOUS,"","","","","","","","","","","","","","");
        return fallbackMushroom;
    }

    public MushroomReceiptDTO  fallbackMushroomDTO(Exception e) {
        logger.info("call mushroom fallback method");
        MushroomReceiptDTO  fallbackMushroom = new MushroomReceiptDTO (1000l, "", "fallback", "", false, Consumable.POISONOUS,"","","","","","","","","","","","","","");
        return fallbackMushroom;
    }

    public void fallbackNull(Exception e) {
        logger.info("call mushroom fallback method");
    }
}
