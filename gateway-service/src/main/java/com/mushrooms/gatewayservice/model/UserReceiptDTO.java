package com.mushrooms.gatewayservice.model;

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
    private String username;
    private String email;
    private String password;
    private String role;

    private String photoURL;
    private String bio;

    public UserReceiptDTO(Long id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
