package com.mushrooms.eventservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EventServiceApplicationTests {

	@MockBean
	private EventServiceApplication application;

	@Test
	void contextLoads() {
	}

}
