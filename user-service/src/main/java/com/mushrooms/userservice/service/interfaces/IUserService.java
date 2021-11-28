package com.mushrooms.userservice.service.interfaces;

import com.mushrooms.userservice.dao.User;
import com.mushrooms.userservice.dto.UserReceiptDTO;
import com.mushrooms.userservice.dto.UserRequestDTO;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    UserReceiptDTO findUserByUsername (String username);

    void deleteUser(String username);

    UserReceiptDTO createUser(UserRequestDTO userRequestDTO);

    UserReceiptDTO updateUser(String username, UserRequestDTO userRequestDTO);

}
