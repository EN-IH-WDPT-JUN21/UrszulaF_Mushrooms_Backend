package com.mushrooms.gatewayservice.service.impl;

import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.service.interfaces.IUserService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class UserService implements IUserService {

    public static final String TARGET_SERVICE = "user-service";
    private WebClient client;
    //    private final WebClient client = WebClient.create("http://localhost:8080");
    final DiscoveryClient discoveryClient;

    public UserService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        createClient();
    }

    private void createClient() {
        var serviceInstanceList = discoveryClient.getInstances(TARGET_SERVICE);
        String clientURI = serviceInstanceList.get(0).getUri().toString();
        client = WebClient.create(clientURI);
    }

    public UserReceiptDTO findByUsername(String username) {
        return client.get()
                .uri("/api/users/" + username)
                .retrieve()
                .bodyToMono(UserReceiptDTO.class)
                .block();
    }


    public List<UserReceiptDTO> findAllUsers() {
        var listOfUsers =
                client //we are using the client
                        .get() //the verb we want to use in the request -- from here
                        .uri("/api/users") // this the url that will be appended ---till here this is the request side
                        .retrieve() // open the response
                        .bodyToMono(new ParameterizedTypeReference<List<UserReceiptDTO>>() {}) // map the response to a list of greetings  -- new ParameterizedTypeReference<List<Greeting is the object we are expecting to receive
                        .block(); // end the transmission

        return listOfUsers;


    }


}
