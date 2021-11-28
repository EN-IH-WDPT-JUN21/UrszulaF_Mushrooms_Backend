package com.mushrooms.userservice.controller;

import com.mushrooms.userservice.dao.User;
import com.mushrooms.userservice.dto.UserReceiptDTO;
import com.mushrooms.userservice.dto.UserRequestDTO;
import com.mushrooms.userservice.repository.UserRepository;
import com.mushrooms.userservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserReceiptDTO findUserByUsername (@PathVariable(name="username") String username){
        return userService.findUserByUsername(username);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReceiptDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }

    @PutMapping ("/update/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserReceiptDTO updateUser(@PathVariable String username, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(username, userRequestDTO);
    }

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }
}
