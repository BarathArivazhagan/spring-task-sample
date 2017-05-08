package com.accenture.adce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ChartConfigurationService {
	
	
	private static final Logger logger=LoggerFactory.getLogger(ChartConfigurationService.class);
	
	@Autowired
	private ApplicationContext context;
	
	private ChartDecorator decorator;
	
	
	public ChartConfiguration handleChartService(String serviceName,String methodName,String chartType){
		
		Assert.notNull(context, "application context cannot be null");
		
		return null;
	}
	
	
	private Class<?> getServiceClass(String serviceName){
		Class<?> serviceClazz=null;
		Object serviceBean=context.getBean(serviceName);
		if(serviceClazz ==null){
			serviceClazz=context.getBean(arg0)
		}
		serviceClazz=serviceBean.getClass();
		return serviceClazz;
	}
	
	
	
	private Object invokeService(){
		
	}

}
