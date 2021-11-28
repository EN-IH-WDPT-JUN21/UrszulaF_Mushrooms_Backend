package com.mushrooms.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {


    private String photoURL;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String role;



}
