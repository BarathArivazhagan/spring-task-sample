package com.barath.app;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="classpath:sql.properties")
@Configuration
@ConfigurationProperties
public class SqlQueries {
	
	private Map<String,String> queries;

	public Map<String, String> getQueries() {
		return queries;
	}

	public void setQueries(Map<String, String> queries) {
		this.queries = queries;
	}

	public SqlQueries(Map<String, String> queries) {
		super();
		this.queries = queries;
	}

	public SqlQueries() {
		super();
		
	}
	
	
	

}
