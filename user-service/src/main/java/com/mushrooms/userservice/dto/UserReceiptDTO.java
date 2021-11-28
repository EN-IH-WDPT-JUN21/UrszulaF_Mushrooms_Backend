package com.mushrooms.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReceiptDTO {

    private Long id;
    private String photoURL;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String role;

    public UserReceiptDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}