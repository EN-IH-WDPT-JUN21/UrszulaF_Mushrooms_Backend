package com.mushrooms.gatewayservice.service.interfaces;

import com.mushrooms.gatewayservice.model.User;
import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.model.UserRequestDTO;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();
    List<UserReceiptDTO> getUsersWithAdds();
    UserReceiptDTO findByUsername(String username);
    UserReceiptDTO findByUsernameWithAdds(String username);
    UserReceiptDTO createUser(UserRequestDTO userRequestDTO);
    UserReceiptDTO createUserWithAdds(UserRequestDTO userRequestDTO);
    void deleteUser(String username);
    void deleteUserWithAdds(String username);
    UserReceiptDTO updateUser(String username, UserRequestDTO userRequestDTO);
    UserReceiptDTO updateUserWithAdds(String username, UserRequestDTO userRequestDTO);

}
