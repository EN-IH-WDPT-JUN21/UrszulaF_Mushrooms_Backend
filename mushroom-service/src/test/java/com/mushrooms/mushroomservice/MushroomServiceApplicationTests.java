package com.mushrooms.mushroomservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MushroomServiceApplicationTests {

	@MockBean
	private MushroomServiceApplication application;

	@Test
	void contextLoads() {
	}

}
