package com.mushrooms.userservice.dao;

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
public class User {
    @Id
    private Long id;
    private String photoURL;
    private String username;
    private String bio;


    public User(String photoURL, String username, String bio) {
        this.photoURL = photoURL;
        this.username = username;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", photoURL='" + photoURL + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
