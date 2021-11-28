package com.mushrooms.gatewayservice.service.interfaces;

import com.mushrooms.gatewayservice.model.User;
import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.model.UserRequestDTO;

import java.util.List;

public interface IUserService {
    List<UserReceiptDTO> findAllUsers();
    UserReceiptDTO findByUsername(String username);



}
