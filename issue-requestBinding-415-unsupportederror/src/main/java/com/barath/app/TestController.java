package com.barath.app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TestController {
	
	private static final Logger logger=LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value="/addCompany",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	   public@ResponseBody String userLogin(@RequestBody CompanyRegVO user1,HttpServletRequest req) throws Exception{
	 
	       ObjectMapper mapper=new ObjectMapper(); 
	      
	   

	    return mapper.writeValueAsString(user1);

	   }

}
