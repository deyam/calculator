package com.example.calculator;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
//@EnableHazelcastRepositories
public class CalculatorApplication {
	private static final String CONSTANT = "constant";

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}
	@Bean
	public ClientConfig hazelcastClientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress("hazelcast");
		clientConfig.getNetworkConfig().getKubernetesConfig().setEnabled(true)
				.setProperty("namespace","default")
				.setProperty("service-name","hazelcast");
		HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(clientConfig);

		return clientConfig;

}


//	@Bean
//	public CacheManager cacheManager() {
//		SimpleCacheManager cacheManager = new SimpleCacheManager();
//		cacheManager.setCaches(Arrays.asList(
////				new ConcurrentMapCache("directory"),
//				new ConcurrentMapCache("sum")));
//		return cacheManager;
//	}

	@Bean
	Config config() {
		Config config = new Config();
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true)
				.setProperty("namespace", "default")
				.setProperty("service-name", "hazelcast");
		return config;
	}

}
