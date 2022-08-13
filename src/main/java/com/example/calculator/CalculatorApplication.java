package com.example.calculator;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class CalculatorApplication {
	private static final String CONSTANT = "constant";

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}
	@Bean
	public ClientConfig hazelcastClientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("hazelcast");
		HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(clientConfig);

		return clientConfig;

}
}
