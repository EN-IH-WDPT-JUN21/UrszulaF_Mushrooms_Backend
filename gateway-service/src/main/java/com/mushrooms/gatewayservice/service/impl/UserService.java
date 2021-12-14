package com.mushrooms.gatewayservice.service.impl;

import com.mushrooms.gatewayservice.model.*;
import com.mushrooms.gatewayservice.proxy.UserProxy;
import com.mushrooms.gatewayservice.repository.UserRepository;
import com.mushrooms.gatewayservice.service.interfaces.IUserService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserProxy userProxy;

//    public static final String TARGET_SERVICE = "user-service";
//    private WebClient client;
//    //    private final WebClient client = WebClient.create("http://localhost:8080");
//    final DiscoveryClient discoveryClient;
//
//    public UserService(DiscoveryClient discoveryClient) {
//        this.discoveryClient = discoveryClient;
//        createClient();
//    }
//
//    private void createClient() {
//        var serviceInstanceList = discoveryClient.getInstances(TARGET_SERVICE);
//        String clientURI = serviceInstanceList.get(0).getUri().toString();
//        client = WebClient.create(clientURI);
//    }

//    public UserReceiptDTO findByUsername(String username) {
//        return client.get()
//                .uri("/api/users/" + username)
//                .retrieve()
//                .bodyToMono(UserReceiptDTO.class)
//                .block();

//    }



    Logger logger = LoggerFactory.getLogger("UserService.class");

    @Retry(name = "user-api", fallbackMethod = "fallbackUserList")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public List<UserReceiptDTO> getUsersWithAdds(){
        List<UserReceiptDTO> userReceiptDTOList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user: users){
            String username = user.getUsername();
            Long id = user.getId();
            var adds = userProxy.findUserById(id);

            UserReceiptDTO userReceiptDTO= new UserReceiptDTO(user.getId(), user.getUsername(),user.getEmail(), user.getPassword(),user.getRole());
            if (!adds.isEmpty()){
                if(adds.get().getUsername().equals(username)){
                    userReceiptDTO.setBio(adds.get().getBio());
                    userReceiptDTO.setPhotoURL(adds.get().getPhotoURL());
                }

            }
            userReceiptDTOList.add(userReceiptDTO);
        }
        return userReceiptDTOList;
    }

    //    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(username);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        User user= optionalUser.isPresent()? optionalUser.get() : null;

        return convertUserToDTO(user);
    }

    public UsernameDTO findUsername(String username) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(username);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        User user= optionalUser.isPresent()? optionalUser.get() : null;

        UsernameDTO usernameDTO=new UsernameDTO(user.getUsername());
        return usernameDTO;
    }

    public RoleDTO findRole(String username) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(username);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        User user= optionalUser.isPresent()? optionalUser.get() : null;

        RoleDTO roleDTO=new RoleDTO(user.getRole());
        return roleDTO;
    }

    public UserReceiptDTO findByUsernameWithAdds(String username) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(username);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        User user= optionalUser.isPresent()? optionalUser.get() : null;
        UserReceiptDTO userReceiptDTO= convertUserToDTO(user);
        Long id = user.getId();
        var adds = userProxy.findUserById(id);

        if (!adds.isEmpty()){
            if(adds.get().getUsername().equals(username)){
                userReceiptDTO.setBio(adds.get().getBio());
                userReceiptDTO.setPhotoURL(adds.get().getPhotoURL());
            }

        }
        return userReceiptDTO;


    }

    public List<User> findByUsernameContaining(String username) {
        List<User> optionalUser = userRepository.findByUsernameContaining(username.toLowerCase().trim());

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + username + " not found!");
        }

        return optionalUser;
    }


    //    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(userRequestDTO.getUsername());

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

    //    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO createUserWithAdds(UserRequestDTO userRequestDTO) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(userRequestDTO.getUsername());

        if(optionalUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + userRequestDTO.getUsername() + " already exist!");
        }
        try{
            User user;
            user = convertDTOToUser(userRequestDTO);
            userRepository.save(user);

            UserReceiptDTO userReceiptDTO= convertUserToDTO(user);
            String username = user.getUsername();
            Long id = user.getId();
            var adds = userProxy.findUserById(id);

            if (adds.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error in database. This id  " + id + " already exist!");
            }
            UserServiceRequestDTO userServiceRequestDTO=new UserServiceRequestDTO(id, userRequestDTO.getPhotoURL(), userRequestDTO.getUsername(), userRequestDTO.getBio());
            userProxy.createUser(userServiceRequestDTO);
            return userReceiptDTO;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User value not valid.");
        }
    }

    //    @Retry(name = "user-api", fallbackMethod = "fallbackNull")
    public void deleteUser(String username) {
        User user = userRepository.findOptionalByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        userRepository.delete(user);
    }

    //    @Retry(name = "user-api", fallbackMethod = "fallbackNull")
    public void deleteUserWithAdds(String username) {
        User user = userRepository.findOptionalByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        Long id = user.getId();
        var adds = userProxy.findUserById(id);
        if (adds.isPresent()){
            if(adds.get().getUsername().equals(username)){
                userProxy.deleteUser(username);
            }
        }
        userRepository.delete(user);

    }

    //    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO updateUser(String username, UserRequestDTO userRequestDTO) {
        User user = userRepository.findOptionalByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        if (userRequestDTO.getEmail() != null && userRequestDTO.getEmail() != "") {
            user.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null && userRequestDTO.getPassword() != "") {
            user.setPassword(userRequestDTO.getPassword());
        }
        if (userRequestDTO.getRole() != null && userRequestDTO.getRole() != "") {
            user.setRole(userRequestDTO.getRole());
        }
        userRepository.save(user);
        return convertUserToDTO(user);
    }

    //    @Retry(name = "user-api", fallbackMethod = "fallbackUserDTO")
    public UserReceiptDTO updateUserWithAdds(String username, UserRequestDTO userRequestDTO) {
        User user = userRepository.findOptionalByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username " + username + " not found!"));
        if (userRequestDTO.getEmail() != null && userRequestDTO.getEmail() != "") {
            user.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null && userRequestDTO.getPassword() != "") {
            user.setPassword(userRequestDTO.getPassword());
        }
        if (userRequestDTO.getRole() != null && userRequestDTO.getRole() != "") {
            user.setRole(userRequestDTO.getRole());
        }
        userRepository.save(user);
        Long id = user.getId();
        var adds = userProxy.findUserById(id);
        if (adds.isPresent()){
            if(adds.get().getUsername().equals(username)){
                UserServiceRequestDTO userServiceRequestDTO=new UserServiceRequestDTO(id, userRequestDTO.getPhotoURL(), userRequestDTO.getUsername(), userRequestDTO.getBio());
                userProxy.updateUser(username, userServiceRequestDTO);
            }
        }
        return convertUserToDTO(user);
    }

    private UserReceiptDTO convertUserToDTO(User user) {
        return new UserReceiptDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
    }

    private User convertDTOToUser(UserRequestDTO userRequestDTO) {
        return new User(
                userRequestDTO.getUsername(),
                userRequestDTO.getEmail(),
                userRequestDTO.getPassword(),
                userRequestDTO.getRole()
        );
    }

    //fallback

    public List<User>  fallbackUserList(Exception e) {
        logger.info("call user fallback method");
        List<User> fallbackUserList = new ArrayList<>();
        User fallbackUser = new User("fallback", "","","");
        fallbackUserList.add(fallbackUser);
        return fallbackUserList;
    }

    public User fallbackUser(Exception e) {
        logger.info("call user fallback method");
        User fallbackUser = new User("fallback", "","","");
        return fallbackUser;
    }

    public UserReceiptDTO  fallbackUserDTO(Exception e) {
        logger.info("call user fallback method");
        UserReceiptDTO  fallbackUser = new UserReceiptDTO (0l, "fallback", "","","");
        return fallbackUser;
    }

    public void fallbackNull(Exception e) {
        logger.info("call user fallback method");
    }
}
