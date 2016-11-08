
package com.demo.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.app.model.Item;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantTaskApplicationTests {	
		
		
		private static final Logger logger=LoggerFactory.getLogger(RestaurantTaskApplication.class);
		
		@Value("${data.file.location:classpath:data.txt}")
		private String fileLocation=null;
		
		
		
		@Autowired
		private TestRestaurantService service;
		
		
		@Autowired
		private ResourceLoader resourceLoader;
		
		


		@Test
		public void testTotalSatisfactionIndex() {
			long totalSatisficationIndex=service.getTotalSatisfactionIndex();
			logger.info("TOTAL INDEX "+totalSatisficationIndex);
		}
		

		@Test
		public void testTotalTimeTaken() {
			long totalTimeTaken=service.getTotalTimeTaken();
			logger.info("TOTAL TIME TAKEN "+totalTimeTaken);
			assertEquals(49879, totalTimeTaken);
		}

		@Test
		public void testMaximumSatisfactionIndex() {
			long maxSatisficationIndex=service.getMaximumSatisfactionIndex();
			logger.info("MAX INDEX "+maxSatisficationIndex);
		}
		
		@Test
		public void testMaximumTimeTaken() {
			long maxTimeTaken=service.getMaximumTimeTaken();
			logger.info("MAX TIME TAKEN "+maxTimeTaken);
		}
		
		
		@Test
		public void testAverageSatisfactionIndex() {
			long avgSatisficationIndex=service.getAverageSatisfactionIndex();
			logger.info("AVG INDEX "+avgSatisficationIndex);
			assertEquals(44880, avgSatisficationIndex);
		}
		
		
		
		@Test
		public void testAverageTimeTakenx() {
			long avgTimeTaken=service.getAverageTimeTaken();
			logger.info("AVG TIME TAKEN "+avgTimeTaken);
			assertEquals(498, avgTimeTaken);
		}
		
		@Test
		public void testLeastSatisfactionIndex() {
			long leastSatisficationIndex=service.getLeastSatisfactionIndex();
			logger.info("Least INDEX "+leastSatisficationIndex);
			assertEquals(261, leastSatisficationIndex);
		}
		
		
		
		@Test
		public void testLeastTimeTakenx() {
			long leastTimeTaken=service.getLeastTimeTaken();
			logger.info("Least TAKEN "+leastTimeTaken);
		}

		
		@Test
		public void loadDataFromClassPathResource() throws IOException{
			
			List<String> lines=new ArrayList<String>();		
			    try{
			    	Resource resource=resourceLoader.getResource(fileLocation);			
			    	InputStream inputStream=resource.getInputStream();		    	
			        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			        String line;
			        while ((line = bufferedReader.readLine()) != null) {
			        	  lines.add(line);
			           
			       	 }
			          bufferedReader.close();
			          inputStream.close();
			    	}catch(IOException e){
			    		logger.error("Exception in reading file "+e.getMessage());
			    		e.printStackTrace();
			    	}		
			
			
		}
		
		@Test
		public  void testKnapSackLogic() throws Exception {
			long totalMinutesEntered=10000;
			long[] satisfactionIndexes= service.getAllSatisfactionIndex().stream().mapToLong(x-> x).toArray();

			long[] timeTakens= service.getAllTimeTaken().stream().mapToLong(x -> x).toArray();		

			long totalSatisfactionIndex=service.knapSackAlgorithm(satisfactionIndexes, timeTakens, totalMinutesEntered);
	      
	       logger.info( "Total Minutes "+totalMinutesEntered+" Total satisfaction index  "+totalSatisfactionIndex);
		       
		}
	
	
	
		
	
		
		
		
		
		


	


}
