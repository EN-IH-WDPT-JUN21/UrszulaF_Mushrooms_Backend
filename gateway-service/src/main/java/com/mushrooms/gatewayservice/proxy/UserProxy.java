package com.mushrooms.gatewayservice.proxy;

import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.model.UserRequestDTO;
import com.mushrooms.gatewayservice.model.UserServiceReceiptDTO;
import com.mushrooms.gatewayservice.model.UserServiceRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@FeignClient("user-service")
public interface UserProxy {
    @GetMapping("/api/users")
    public List<UserServiceReceiptDTO> findAllUsers();

    @GetMapping("/api/users/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserServiceReceiptDTO findUserByUsername (@PathVariable(name="username") String username);

    @PostMapping("/api/users/new")
    @ResponseStatus(HttpStatus.CREATED)
    public UserServiceReceiptDTO createUser(@RequestBody @Valid UserServiceRequestDTO userServiceRequestDTO);

    @PutMapping ("/api/users/update/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserServiceReceiptDTO updateUser(@PathVariable String username, @RequestBody UserServiceRequestDTO userServiceRequestDTO);

    @DeleteMapping("/api/users/delete/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String username);

    @GetMapping("/api/users/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<UserServiceReceiptDTO> findUserById (@PathVariable(name="id") Long id);

}
