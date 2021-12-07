package com.mushrooms.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String photoURL;
    private String username;
    private String bio;

    public UserRequestDTO(String username) {
        this.username = username;
    }
}
