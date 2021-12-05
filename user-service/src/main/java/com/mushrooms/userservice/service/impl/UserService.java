package com.mushrooms.userservice.service.impl;

import com.mushrooms.userservice.dao.User;
import com.mushrooms.userservice.dto.UserReceiptDTO;
import com.mushrooms.userservice.dto.UserRequestDTO;
import com.mushrooms.userservice.repository.UserRepository;
import com.mushrooms.userservice.service.interfaces.IUserService;
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
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;


    Logger logger = LoggerFactory.getLogger("UserService.class");

    @Retry(name = "user-api", fallbackMethod = "fallbackUserList")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        User user= optionalUser.isPresent()? optionalUser.get() : null;

        return convertUserToDTO(user);
    }

//    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(userRequestDTO.getUsername());

        if(optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + userRequestDTO.getUsername() + " already exist!");
        }
        try{
            User user;
            user = convertDTOToUser(userRequestDTO);
            userRepository.save(user);
            return convertUserToDTO(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User type value not valid.");
        }
    }

//    @Retry(name = "user-api", fallbackMethod = "fallbackNull")
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        userRepository.delete(user);
    }

//    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO updateUser(String username, UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        if (userRequestDTO.getPhotoURL() != null && userRequestDTO.getPhotoURL() != "") {
            user.setPhotoURL(userRequestDTO.getPhotoURL());
        }
        if (userRequestDTO.getEmail() != null && userRequestDTO.getEmail() != "") {
            user.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null && userRequestDTO.getPassword() != "") {
            user.setPassword(userRequestDTO.getPassword());
        }
        if (userRequestDTO.getBio() != null && userRequestDTO.getBio() != "") {
            user.setBio(userRequestDTO.getBio());
        }
        if (userRequestDTO.getRole() != null && userRequestDTO.getRole() != "") {
            user.setRole(userRequestDTO.getRole());
        }
        userRepository.save(user);
        return convertUserToDTO(user);
    }

    private UserReceiptDTO convertUserToDTO(User user) {
        return new UserReceiptDTO(user.getId(), user.getPhotoURL(), user.getUsername(), user.getEmail(), user.getPassword(), user.getBio(), user.getRole());
    }

    private User convertDTOToUser(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.getPhotoURL(),
                userRequestDTO.getUsername(), 
                userRequestDTO.getEmail(), 
                userRequestDTO.getPassword(), 
                userRequestDTO.getBio(), 
                userRequestDTO.getRole()
        );
    }

    //fallback

    public List<User>  fallbackUserList(Exception e) {
        logger.info("call user fallback method");
        List<User> fallbackUserList = new ArrayList<>();
        User fallbackUser = new User("", "fallback", "","","","");
        fallbackUserList.add(fallbackUser);
        return fallbackUserList;
    }

    public User fallbackUser(Exception e) {
        logger.info("call user fallback method");
        User fallbackUser = new User("", "fallback", "","","","");
        return fallbackUser;
    }

    public UserReceiptDTO  fallbackUserDTO(Exception e) {
        logger.info("call user fallback method");
        UserReceiptDTO  fallbackUser = new UserReceiptDTO (0l, "", "fallback", "","","","");
        return fallbackUser;
    }

    public void fallbackNull(Exception e) {
        logger.info("call user fallback method");
    }
}
