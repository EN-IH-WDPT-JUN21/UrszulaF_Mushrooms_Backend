package com.mushrooms.gatewayservice.controller;

import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Home {

    @Autowired
    IUserService userService;


    @RequestMapping("/welcome")
    public String welcome() {
        String text = "this is private page ";
        text+= "this page is not allowed to unauthenticated users";
        return text;
    }


//    @RequestMapping("/getusers")
//    public String getUser() {
//        return "{\"name\":\"Durgesh\"}";
//    }

    @GetMapping("/getusers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserReceiptDTO> findAllUsers(){
        return userService.findAllUsers();
    }


}

