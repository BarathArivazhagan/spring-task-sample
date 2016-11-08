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
		public void testLogic(){
			
			
			long totalMinutesEntered=10000;			
			long totalSatisfactionIndex=0;
			long leastTimeTaken=service.getLeastTimeTaken();
			long timeDifference=totalMinutesEntered;
			List<Item> items=service.getConsolidatedItemsWithMaxSatisfacionIndex();
				
			List<Long> timesTaken=new ArrayList<Long>();
			List<Item> finalItems=new  ArrayList<Item>();
			while(timeDifference >= leastTimeTaken){			
					Item nearestItem=null;
					List<Item> remainingItems=service.getRemainingItemsList(items,timesTaken,timeDifference);
					
					if( remainingItems !=null && !remainingItems.isEmpty()){
						nearestItem =service.findBestNearestItem(timeDifference,remainingItems);
				
						if(!service.isTimeTakenAlready(nearestItem.getTimeTakenCombos(), timesTaken)){
							timesTaken.addAll(nearestItem.getTimeTakenCombos());							
							totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
						}else{							
							nearestItem=service.findBestWithinRemainingItems(remainingItems);
							totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
							timesTaken.add( nearestItem.getTimeTaken());
						}
						timeDifference -= service.sumTotalTimeTaken(nearestItem);
					
						
					}else{
						timeDifference=0;
					}
						
					if(nearestItem !=null){
						finalItems.add(new Item(nearestItem.getItemNo(),totalSatisfactionIndex,nearestItem.getTimeTaken(),timesTaken));
					}
				}		
				
			
				logger.info("Minutes  "+totalMinutesEntered);
				items.stream().forEach(System.out::println);				
				finalItems.stream().forEach(System.out::println);
			
				System.out.println("Total Minutes "+totalMinutesEntered+" Total satisfaction index  "+totalSatisfactionIndex);
			
		}		


		
		@Test
		public void getConsolidatedItemsWithMaxSatisfacionIndex(){
			List<Item> items=service.findItemsWithLeastTimeTakenSorted();
			long leastTimeTaken=service.getLeastTimeTaken();
			List<Item> itemsWithMaxSatisfactionIndex=new LinkedList<Item>();
			for(int i=0;i<items.size();i++){			
				if(i == 0){						
					Item item=items.get(i);
					item.setTimeTakenCombos(Arrays.asList(item.getTimeTaken()));
					itemsWithMaxSatisfactionIndex.add(item);
					continue;
				}
				long totalSatisfactionIndex=0;										
				Item currentItem= items.get(i);
				Item previousItem=items.get(i-1);
				long timeDifference=currentItem.getTimeTaken();
				//long timeDifference=enteredMinutes;
				Item nearestItem=null;
				if(logger.isDebugEnabled()){
					logger.debug("Current ITEM "+currentItem.toString());
					logger.debug("Previous ITEM "+previousItem.toString());
				}
				
				
				List<Long> timesTaken=new ArrayList<Long>();
				do{
					
					List<Item> remainingItems=service.getRemainingItemsList(itemsWithMaxSatisfactionIndex,timesTaken,timeDifference);
					
					if( remainingItems !=null && !remainingItems.isEmpty()){
						nearestItem =service.findBestNearestItem(timeDifference,remainingItems);
				
						if(!service.isTimeTakenAlready(nearestItem.getTimeTakenCombos(), timesTaken)){
							timesTaken.addAll(nearestItem.getTimeTakenCombos());							
							totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
						}else{
						
							nearestItem=service.getItem(service.findBestWithinRemainingItems(remainingItems).getTimeTaken());
							totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
							timesTaken.add( nearestItem.getTimeTaken());
						}
						timeDifference -= service.sumTotalTimeTaken(nearestItem);
					
						
					}else{
						timeDifference=0;
					}
						System.out.println("time difference "+timeDifference);
						System.out.println("Satisfaction indsex  "+totalSatisfactionIndex);
					
					
				}while(timeDifference >= leastTimeTaken);
				
				
				if(currentItem.getSatisfactionIndex() > totalSatisfactionIndex){
					totalSatisfactionIndex=currentItem.getSatisfactionIndex();
						timesTaken.clear();
						timesTaken.add(currentItem.getTimeTaken());
				}
				
					
						
				
				
				itemsWithMaxSatisfactionIndex.add(new Item(i,totalSatisfactionIndex,currentItem.getTimeTaken(),timesTaken));			
			
			}
			
			
		}	
		
		
	
		
	
		
		
		
		
		


	


}
