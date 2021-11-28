package com.mushrooms.gatewayservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_AUTH")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoURL;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String role;


    public User(String photoURL, String username, String email, String password, String bio, String role) {
        this.photoURL = photoURL;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", photoURL='" + photoURL + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
