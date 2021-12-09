package com.mushrooms.gatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GatewayServiceApplicationTests {

	@MockBean
	private GatewayServiceApplication application;

	@Test
	void contextLoads() {
	}

}
