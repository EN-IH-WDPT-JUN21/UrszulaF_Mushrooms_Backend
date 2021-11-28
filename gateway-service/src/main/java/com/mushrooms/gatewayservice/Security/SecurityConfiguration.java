/*
package com.mushrooms.gatewayservice.Security;

import com.mushrooms.gatewayservice.dto.UserReceiptDTO;
import com.mushrooms.gatewayservice.service.impl.CustomUserDetailsService;
import com.mushrooms.gatewayservice.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        http.csrf().disable();

        http.authorizeExchange()
                .pathMatchers("/api/users**").hasRole("ADMIN")
//                .pathMatchers( "/api/users/**").permitAll()
//                .pathMatchers("/api/users**").permitAll()
                .pathMatchers("/**").permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        List<UserDetails> myUsers = new ArrayList<>();
        UserDetails user1 = User
                .withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
        myUsers.add(user1);
        myUsers.add(admin);
        List<UserReceiptDTO> entities = userService.findAllUsers();
        for(UserReceiptDTO entity: entities){
            UserDetails user = User
                    .withUsername(entity.getUsername())
                    .password(passwordEncoder.encode(entity.getPassword()))
                    .roles(entity.getRole())
                    .build();
            myUsers.add(user);
        }
        System.out.println(myUsers);

        return new MapReactiveUserDetailsService(myUsers);
    }


}*/
