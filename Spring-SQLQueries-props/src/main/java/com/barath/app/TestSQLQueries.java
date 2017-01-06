package com.barath.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@ConditionalOnBean(value=SqlQueries.class)
@Component
public class TestSQLQueries {
	
	@Autowired
	private SqlQueries queries;
	
	@PostConstruct
	public void init(){
	
		queries.getQueries().entrySet().stream().forEach(entry->{
			System.out.println(entry.getKey()+" "+entry.getValue());
		});
	}

}
