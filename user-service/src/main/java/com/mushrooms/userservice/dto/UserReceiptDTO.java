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
    private String bio;

    public UserReceiptDTO(String username) {
        this.username = username;
    }
}
