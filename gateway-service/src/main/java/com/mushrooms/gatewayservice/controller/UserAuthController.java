package com.mushrooms.gatewayservice.controller;

import com.mushrooms.gatewayservice.model.*;
import com.mushrooms.gatewayservice.repository.UserRepository;
import com.mushrooms.gatewayservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users-auth")
public class UserAuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserReceiptDTO> getUsersWithAdds(){
        return userService.getUsersWithAdds();
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserReceiptDTO findUserByUsername (@PathVariable(name="username") String username){
        return userService.findByUsernameWithAdds(username);
    }

    @GetMapping("name/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UsernameDTO findUsername (@PathVariable(name="username") String username){
        return userService.findUsername(username);
    }

    @GetMapping("role/{username}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO findRole (@PathVariable(name="username") String username){
        return userService.findRole(username);
    }

    @GetMapping("/containing/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findByUserNameContaining (@PathVariable(name="username") String username){
        return userService.findByUsernameContaining(username);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReceiptDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.createUserWithAdds(userRequestDTO);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReceiptDTO createUserNoAdds(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }

    @PutMapping ("/update/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserReceiptDTO updateUser(@PathVariable String username, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUserWithAdds(username, userRequestDTO);
    }

    @DeleteMapping("/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String username) {
        userService.deleteUserWithAdds(username);
    }
}
