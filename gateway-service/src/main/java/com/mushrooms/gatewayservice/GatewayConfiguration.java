//
//package com.mushrooms.gatewayservice;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//public class GatewayConfiguration {
//
//    @Bean
//    CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8000", "http://localhost:8100", "http://localhost:8200", "http://localhost:8300"));
//        corsConfig.addAllowedMethod("GET");
//        corsConfig.addAllowedMethod("PUT");
//        corsConfig.addAllowedMethod("POST");
//        corsConfig.addAllowedMethod("PATCH");
//        corsConfig.addAllowedMethod("DELETE");
//        corsConfig.addAllowedMethod("HEAD");
//        corsConfig.addAllowedMethod("OPTIONS");
//        corsConfig.addAllowedHeader("*");
//
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }
//
//    @Bean
//    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//                // Mushrooms Service
//                .route(p -> p.path("/api/mushrooms/**")
//                        .uri("lb://MUSHROOM-SERVICE"))
//                .route(p -> p.path("/api/mushrooms**")
//                        .uri("lb://MUSHROOM-SERVICE"))
//
//                // Events Service
//                .route(p -> p.path("/api/events/**")
//                        .uri("lb://EVENT-SERVICE"))
//                .route(p -> p.path("/api/events**")
//                        .uri("lb://EVENT-SERVICE"))
//
//                // User Service
//                .route(p -> p.path("/api/users/**")
//                        .uri("lb://USER-SERVICE"))
//                .route(p -> p.path("/api/users**")
//                        .uri("lb://USER-SERVICE"))
//
//                .build();
//    }
//}

