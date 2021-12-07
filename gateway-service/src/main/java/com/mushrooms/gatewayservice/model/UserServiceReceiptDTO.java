package com.mushrooms.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceReceiptDTO {
    private Long id;
    private String photoURL;
    private String username;
    private String bio;
}
