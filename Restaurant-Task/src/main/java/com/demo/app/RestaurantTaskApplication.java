package com.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * This is the main class for the application to start the spring container{@link SpringBootApplication}
 * and also acts as rest controller {@link RestController} to handle 
 * the application specific request mappings
 * 
 * @author barath
 *
 */
@SpringBootApplication
@RestController
public class RestaurantTaskApplication {
	
		
		private static final Logger logger=LoggerFactory.getLogger(RestaurantTaskApplication.class);
		
		@Autowired
		private RestaurantService service;
		
		

		public static void main(String[] args) {
			SpringApplication.run(RestaurantTaskApplication.class, args);
		}
			
		/**
		 * Request handler method with mapping /{minutes}
		 * to handle the logic based on the @param minutes passed
		 * 
		 * @param minutes
		 * @return String - outputs the maximum satisfaction index for the  @param minutes
		 */
		@GetMapping("/{minutes}")
		public String handleLogic(@PathVariable("minutes") Long minutes){
			String outputMessage=null;
			
			if(!StringUtils.isEmpty(minutes)){
				outputMessage=service.handleLogic(minutes);
			}else{
				outputMessage="Wrong input passed, Please pass in correct input";
			}
			if(logger.isInfoEnabled()){
				logger.info("Minutes entered "+minutes);
				logger.info("Output Message "+outputMessage);
			}
			return outputMessage;
		}
		
		/**
		 * Request handler method with mapping /
		 * to welcome the customer
		 * 
		 * @param minutes
		 * @return String
		 */
		@GetMapping("/")
		public String welcome(){
			
			return "Welcome Mr.Gordon Ramsey to the Restaurant";
		}
		
		/**
		 * This method is to handle the exceptions thrown by the controller advice
		 * {@link ControllerAdvice}
		 * @param ex
		 * @return String
		 */
		@ExceptionHandler(Exception.class)
		public String handleException(Exception ex){
			return ex.getMessage();
		}
		
	
}
