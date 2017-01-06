package com.barath.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class,DataSourceAutoConfiguration.class})
public class SpringSqlQueriesPropsApplication implements CommandLineRunner {
	
	

	public static void main(String[] args) {
		SpringApplication.run(SpringSqlQueriesPropsApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	
		
	}
	
	
}
