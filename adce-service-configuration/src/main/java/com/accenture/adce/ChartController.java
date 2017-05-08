package com.accenture.adce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {
	
	
	private static final Logger logger=LoggerFactory.getLogger(ChartController.class);
	
	@Autowired
	private ChartConfigurationService chartService;
	
	
	
	@RequestMapping(value="/chart/service")
	public ChartConfiguration handleChartConfig(@RequestParam("serviceName") String serviceName,
			@RequestParam("methodName") String methodName,
			@RequestParam("chartType") String chartType) throws ChartConfigurationException{
		
		if(StringUtils.isEmpty(serviceName)){
			throw new ChartConfigurationException("service name cannot be empty");
		}else if(StringUtils.isEmpty(methodName)){
			throw new ChartConfigurationException("method name cannot be empty");
		}else if(StringUtils.isEmpty(chartType)){
			throw new ChartConfigurationException("chart type cannot be empty");
		}		
		return chartService.handleChartService(serviceName, methodName, chartType);
	}
	
	
	@ExceptionHandler(ChartConfigurationException.class)
	public String handleChartConfException(ChartConfigurationException ex){
		
		logger.error("Exception in configuring the chart service "+ex.getMessage());
		ex.printStackTrace();
		return ex.getMessage();
	}
	

}
