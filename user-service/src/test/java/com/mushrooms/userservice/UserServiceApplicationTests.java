package com.mushrooms.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceApplicationTests {

	@MockBean
	private UserServiceApplication application;

	@Test
	void contextLoads() {
	}

}
