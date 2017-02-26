package com.barath.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class EurekaClientLogServicesApplication implements CommandLineRunner {
	
	
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientLogServicesApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		String level=env.getProperty("level");
		System.out.println("level value is "+level);
		
		log.error("Error message");
		log.info("info message");
		log.debug("debug message");
		
		
	}
	
	@GetMapping("/")
	public void test(){
		

		log.error("Error message from controller");
		log.info("info message from controller ");
		log.debug("debug message from controller");
		
	}
	
	
}
