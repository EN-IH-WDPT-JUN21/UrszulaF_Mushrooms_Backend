package com.mushrooms.animalservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AnimalServiceApplicationTests {

	@MockBean
	private AnimalServiceApplication application;


	@Test
	void contextLoads() {
	}

}
