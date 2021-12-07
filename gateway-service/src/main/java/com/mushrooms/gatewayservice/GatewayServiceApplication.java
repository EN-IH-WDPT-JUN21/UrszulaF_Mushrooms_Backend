package com.mushrooms.gatewayservice;

import com.mushrooms.gatewayservice.model.User;
import com.mushrooms.gatewayservice.model.UserReceiptDTO;
import com.mushrooms.gatewayservice.repository.UserRepository;
import com.mushrooms.gatewayservice.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class GatewayServiceApplication /*implements CommandLineRunner*/ {

//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private UserService userService;
//
//	Random random = new Random();

//	public void createUsers() {

//		User user = new User();
//		Long id = new Long(random.nextInt(100));
//		user.setId(id);
//		user.setEmail("user" + id + "@gmail.com");
//		user.setPassword("user" + id);
////		user.setEnabled(true);
//		user.setRole("Admin");
//		user.setUsername("user" + id);
//		User save = this.userRepository.save(user);
//
//		System.out.println(save);


//		List<User> myUsers = new ArrayList<>();
//
//		List<UserReceiptDTO> entities = userService.findAllUsers();
//		for(UserReceiptDTO entity: entities){
//			User userT = new User();
//			userT.setId(entity.getId());
//			userT.setEmail(entity.getEmail());
//			userT.setPassword(entity.getPassword());
////		user.setEnabled(true);
//			userT.setRole(entity.getRole());
//			userT.setUsername(entity.getUsername());
//			User saveT = this.userRepository.save(userT);
//		}
//		System.out.println(myUsers);

//	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		createUsers();
//	}
}
