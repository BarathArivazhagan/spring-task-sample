package com.demo.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.demo.app.model.Item;
import com.demo.app.model.Restaurant;
/**
 * This is the test service class to perform computations in Restaurant class
 * 
 * Also see {@link Restaurant}
 * @author barath
 *
 */
@Service
public class TestRestaurantService {
	
	private static final Logger logger=LoggerFactory.getLogger(RestaurantService.class);
	private Restaurant restaurant=null;
	private Set<Item> items=null;
	
	
	/**
	 * Constructor with argument {@link Restaurant} 
	 * 
	 * @param restaurant
	 */
	@Autowired
	public TestRestaurantService(Restaurant restaurant){
		this.restaurant= restaurant;
		Assert.notNull(restaurant.getItems(), "Items cannot be null. Atleast one item should be there");
		this.items= restaurant.getItems();
	}
	
	/**
	 * This method is used to find maximum satisfaction index based on minutes entered 
	 * as per knapsack algorithm
	 * @param totalMinutesEntered
	 * @return
	 */
	public String handleKnapSackLogic(long totalMinutesEntered){
		
		long[] satisfactionIndexes= this.getAllSatisfactionIndex().stream().mapToLong(x-> x).toArray();

		long[] timeTakens= this.getAllTimeTaken().stream().mapToLong(x -> x).toArray();		

		long totalSatisfactionIndex=knapSackAlgorithm(satisfactionIndexes, timeTakens, totalMinutesEntered);
      
       return "Total Minutes "+totalMinutesEntered+" Total satisfaction index  "+totalSatisfactionIndex;
	}
	

	/**
	 * This method is used to populate the matrix as per knapsack algorithm
	 * @param satisfactionIndexes
	 * @param timeTakens
	 * @param enteredMinutes
	 * @return
	 */
	  public long knapSackAlgorithm(long[] satisfactionIndexes, long[] timeTakens, long enteredMinutes) {
	    	
		
		  	int totalNoOfItems = timeTakens.length;
	    	long[][] V = new long[totalNoOfItems + 1][(int) (enteredMinutes + 1)];	      
	        for (int col = 0; col <= enteredMinutes; col++) {
	            V[0][col] = 0;	        }
	        
	        for (int row = 0; row <= totalNoOfItems; row++) {
	            V[row][0] = 0;
	        }
	        for (int item=1;item<=totalNoOfItems;item++){
	          
	            for (int weight=1;weight<=enteredMinutes;weight++){
	              
	                if (timeTakens[item-1]<=weight){
	                  
	                    V[item][weight]=Math.max (satisfactionIndexes[item-1]+V[item-1][(int) (weight-timeTakens[item-1])], V[item-1][weight]);
	                }
	                else {
	                   
	                    V[item][weight]=V[item-1][weight];
	                }
	            }
	        }

	        return V[totalNoOfItems][ (int) enteredMinutes];
	    }
	  
	  
	
	/**
	 * This method handles the logic of finding the maximum satisfaction index 
	 * for the entered minutes
	 * 
	 * @param enteredMinutes
	 * @return String 
	 */
	public String handleLogic(long totalMinutesEntered){	
		
					
		long totalSatisfactionIndex=0;
		long leastTimeTaken=this.getLeastTimeTaken();
		long timeDifference=totalMinutesEntered;
		List<Item> items=getConsolidatedItemsWithMaxSatisfacionIndex();				
		List<Long> timesTaken=new ArrayList<Long>();
		
		while(timeDifference >= leastTimeTaken){			
				Item nearestItem=null;
				List<Item> remainingItems=getRemainingItemsList(items,timesTaken,timeDifference);
				
				if( remainingItems !=null && !remainingItems.isEmpty()){
					nearestItem =findBestNearestItem(timeDifference,remainingItems);
			
					if(!isTimeTakenAlready(nearestItem.getTimeTakenCombos(), timesTaken)){
						timesTaken.addAll(nearestItem.getTimeTakenCombos());						
						totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
					}else{						
						nearestItem=this.getItem(findBestWithinRemainingItems(remainingItems).getTimeTaken());
						totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
						timesTaken.add( nearestItem.getTimeTaken());
					}
					timeDifference -= sumTotalTimeTaken(nearestItem);
				
					
				}else{
					timeDifference=0;
				}
					
				
			}			
					
			
			if(logger.isDebugEnabled()){
				logger.debug("Total Minutes "+totalMinutesEntered+" Total satisfaction index  "+totalSatisfactionIndex);
			}
			return "Total Minutes "+totalMinutesEntered+" Total satisfaction index  "+totalSatisfactionIndex;
		 
	}
	
	
	/**
	 * This method populates the items with best possible time taken strategy starting with least time taken from the given data
	 * 
	 * @return list of Items see {@link Item}
	 */
	public List<Item> getConsolidatedItemsWithMaxSatisfacionIndex(){
		
		List<Item> items=this.findItemsWithLeastTimeTakenSorted();
		long leastTimeTaken=this.getLeastTimeTaken();
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
			Item nearestItem=null;
			if(logger.isDebugEnabled()){
				logger.debug("Current ITEM "+currentItem.toString());
				logger.debug("Previous ITEM "+previousItem.toString());			
			}	
			List<Long> timesTaken=new ArrayList<Long>();
			do{				
				List<Item> remainingItems=getRemainingItemsList(itemsWithMaxSatisfactionIndex,timesTaken,timeDifference);			
				if( remainingItems !=null && !remainingItems.isEmpty()){
					nearestItem =findBestNearestItem(timeDifference,remainingItems);			
					if(!isTimeTakenAlready(nearestItem.getTimeTakenCombos(), timesTaken)){
						timesTaken.addAll(nearestItem.getTimeTakenCombos());						
						totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
					}else{						
						nearestItem=this.getItem(findBestWithinRemainingItems(remainingItems).getTimeTaken());
						totalSatisfactionIndex += nearestItem.getSatisfactionIndex();
						timesTaken.add( nearestItem.getTimeTaken());
					}
					timeDifference -= sumTotalTimeTaken(nearestItem);					
				}else{
					timeDifference=0;
				}				
				
			}while(timeDifference >= leastTimeTaken);
			
			
			if(currentItem.getSatisfactionIndex() > totalSatisfactionIndex){
				totalSatisfactionIndex=currentItem.getSatisfactionIndex();
					timesTaken.clear();
					timesTaken.add(currentItem.getTimeTaken());
			}		
			
			itemsWithMaxSatisfactionIndex.add(new Item(i,totalSatisfactionIndex,currentItem.getTimeTaken(),timesTaken));			
		
		}
		
		return itemsWithMaxSatisfactionIndex;
	}	
	
	
	/**
	 * This  method finds the best item with maximum satisfaction index from the list of items
	 * 
	 * @param items
	 * @return an instance of Item see {@link Item}
	 */
	public Item findBestWithinRemainingItems(List<Item> items){	
		
		Item bestItem=null;
		List<Long> timeTakens=items.stream().map(Item::getTimeTaken).collect(Collectors.toList());
		List<Item> actualItems=new LinkedList<Item>();			
		for(Long timeTaken:timeTakens){
			actualItems.add(this.getItem(timeTaken));
		}	
		long maxIndex=actualItems.stream().map(Item::getSatisfactionIndex).max((item1,item2) -> Long.compare(item1,item2)).get();
		bestItem=this.getItem(maxIndex);		
		return bestItem;
		
	}
	
	/**
	 * This method finds the list of items below the time difference and removing the used time`s  
	 * 
	 * @param itemsWithMaxSatisfactionIndex
	 * @param timesTaken
	 * @param timeDifference
	 * @return
	 */
	public List<Item> getRemainingItemsList(List<Item> itemsWithMaxSatisfactionIndex, List<Long> timesTaken,long timeDifference) {
		List<Item> modifiedItemsWithMaxSatisfactionIndex=new LinkedList<Item>(itemsWithMaxSatisfactionIndex);
		for(Long timeTaken: timesTaken){
			modifiedItemsWithMaxSatisfactionIndex.removeIf(item -> item.getTimeTaken() == timeTaken);
			
		}		
		return modifiedItemsWithMaxSatisfactionIndex.stream().filter(item -> item.getTimeTaken() <= timeDifference).collect(Collectors.toList());
	}

	/**
	 * This method is to find out whether the time is already consumed within the list of times
	 * 
	 * @param time
	 * @param times
	 * @return
	 */
	public boolean isTimeTakenAlready(long time,List<Long>  times){
		return times.stream().filter(t -> t == time).count() > 1  ?  true: false;		
	}
	
	
	/**
	 * This method is to find out whether the time/time`s is already consumed by comparing two lists of minutes
	 * 
	 * @param time
	 * @param times
	 * @return
	 */
	public boolean isTimeTakenAlready(List<Long>  times,List<Long>  timesTaken){
		
		for(Long timeTaken: timesTaken){
			for(Long time: times ){
				if(timeTaken == time ){					
					return true;
				}
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * 
	 * This method is used to sum all the combination of times used for an item
	 * 
	 * @param item
	 * @return
	 */
	public long sumTotalTimeTaken(Item item){
		
			if(item.getTimeTakenCombos() ==null ){
				return item.getTimeTaken();
			}
			return item.getTimeTakenCombos().stream().mapToLong(Long::longValue).sum();
			
	}
	
	/**
	 * This method removes the @param minutes  from @param minutesToExclude list 
	 * 
	 * @param minutes
	 * @param minutesToExclude
	 * @return
	 */
	public List<Long> getRemainingTimes(List<Long> minutes,List<Long> minutesToExclude){	
		
		for(Long minuteToExclude:minutesToExclude){				
				minutes.removeIf(d -> d ==minuteToExclude);		
		}
		
		return minutes;
	}
	
	/**This method finds the best nearest item from the list of @param items  passed by considering  @param time 
	 * 
	 * @param time
	 * @param items
	 * @return
	 */
	public Item findBestNearestItem(long time,List<Item> items){
		
		Item bestNearestItem=findNearestItem(time,items);
		long bestSatisfactionIndex=bestNearestItem.getSatisfactionIndex();
		long bestTimeTaken=bestNearestItem.getTimeTaken();
		List<Item> bestItems=items.stream().filter(item -> item.getSatisfactionIndex() >= bestSatisfactionIndex && item.getTimeTaken() <= bestTimeTaken)
					.collect(Collectors.toList());
		if(bestItems !=null && !bestItems.isEmpty()){
			bestNearestItem = bestItems.get(0);
		}
		
		return bestNearestItem;
	}
	
	
	/**
	 * This method finds the  nearest item from the list of @param items  passed by considering  @param time 
	 * 
	 * @param time
	 * @param items
	 * @return
	 */
	public Item findNearestItem(long time,List<Item> items){
		Item nearestItem=null;
		 List<Item> itemsFound= items.stream().filter(item -> item.getTimeTaken() < time ).collect(Collectors.toList());
		 if(itemsFound != null && !itemsFound.isEmpty()){	
			
				nearestItem=itemsFound.get(itemsFound.size() -1 );	
			
		 }else{
			 nearestItem=items.stream().findFirst().get();
		 }
			
		return nearestItem;
	}
	

	
	/**
	 * This method finds the best nearest item from the list of @param items  passed by considering  @param time and @param remaingTimeTakens
	 * @param time
	 * @param items
	 * @param remaingTimeTakens
	 * @return
	 */
	public Item findBestNearestItem(long time,List<Item> items,List<Long> remaingTimeTakens){
		
		Item bestNearestItem=findNearestItem(time,items);
		long bestSatisfactionIndex=bestNearestItem.getSatisfactionIndex();
		long bestTimeTaken=bestNearestItem.getTimeTaken();
		List<Item> bestItems=items.stream().filter(item -> item.getSatisfactionIndex() >= bestSatisfactionIndex && item.getTimeTaken() <= bestTimeTaken)
					.collect(Collectors.toList());
		if(bestItems !=null && !bestItems.isEmpty()){
			bestNearestItem = bestItems.get(0);
		}
		
		return bestNearestItem;
	}
	
	/**
	 * This method find the list of time taken`s from the @param items
	 * 
	 * @param items
	 * @return list of time taken`s 
	 */
	public List<Long> getListOfMinutes(List<Item> items){
		return items.stream().map(Item::getTimeTaken).collect(Collectors.toList());
	}
	
	
	/**
	 * This method is used to sort the list of items starting from least time taken 
	 * 
	 * @return list of items
	 */
	public List<Item> findItemsWithLeastTimeTakenSorted(){
		Set<Item> items=this.getItems();
		List<Long> minutes=items.stream().map(Item::getTimeTaken).collect(Collectors.toList());;
		Collections.sort(minutes);		
		Set<Item> itemsSorted=new LinkedHashSet<Item>(); 
		
		minutes.stream().forEach(timeTaken ->{
			Item item=this.getItem(timeTaken);
			itemsSorted.add(item);
			
		});
		
		return itemsSorted.stream().collect(Collectors.toList());
		
		
	}
	
	
	
	
	
	
	public Item findItemWithHighestIndexWithLeastTime(Set<Item> items){
		List<Long> satisfactionIndexes=items.stream().map(Item::getSatisfactionIndex).collect(Collectors.toList());
		Collections.sort(satisfactionIndexes,Collections.reverseOrder());
		Item item=this.getItem(satisfactionIndexes.stream().findFirst().get());
		if(logger.isInfoEnabled()){
			logger.info("Item found "+item);
		}
		return item;
	}
	
	public Item findItemWithHighestIndexWithLeastTime(List<Item> items, List<Long> times){
		List<Long> satisfactionIndexes=items.stream().map(Item::getSatisfactionIndex).collect(Collectors.toList());
		Collections.sort(satisfactionIndexes,Collections.reverseOrder());
		Item item=this.getItem(satisfactionIndexes.stream().findFirst().get());
		if(logger.isInfoEnabled()){
			logger.info("Item found "+item);
		}
		return item;
	}
	
	
	
	/**
	 * This method is used to find total satisfaction index of all the items
	 * 
	 * @return long 
	 */
	public long getTotalSatisfactionIndex(){
		return items.parallelStream().mapToLong(Item::getSatisfactionIndex).sum();
	}
	
	/**
	 * This method is used to find total avaergaeSatisfactionIndex index of all the items
	 * 
	 * @return long 
	 */
	public long getAverageSatisfactionIndex(){
		return getTotalSatisfactionIndex()/getNoOfItems();
	}
	
	/**
	 * This method is used to find total time taken  of all the items
	 * 
	 * @return long 
	 */
	public long getTotalTimeTaken(){
		return items.parallelStream().mapToLong(Item::getTimeTaken).sum();
	}
	
	
	/**
	 * This method is used to find average time taken  of all the items
	 * 
	 * @return long  
	 */
	public long getAverageTimeTaken(){
		return getTotalTimeTaken()/getNoOfItems();
	}
	
	/**
	 * This method is used to maximum satisfaction index from list of items
	 * 
	 * @return long  
	 */
	public long getMaximumSatisfactionIndex(){		
		
		List<Long> satisfactionIndexes=items.stream().map(Item::getSatisfactionIndex).collect(Collectors.toList());
		Collections.sort(satisfactionIndexes,Collections.reverseOrder());
		return satisfactionIndexes.stream().findFirst().get();
	}
	
	/**
	 * This method is used to maximum time taken  from list of items
	 * 
	 * @return long  
	 */
	public long getMaximumTimeTaken(){
		
		
		List<Long> times=items.stream().map(Item::getTimeTaken).collect(Collectors.toList());
		Collections.sort(times,Collections.reverseOrder());
		return times.stream().findFirst().get();
	}
	
	/**
	 * This method is used to least time taken  from list of items
	 * 
	 * @return long  
	 */
	public long getLeastTimeTaken(){
		
		
		List<Long> times=items.stream().map(Item::getTimeTaken).collect(Collectors.toList());
		Collections.sort(times);
		return times.stream().findFirst().get();
	}
	
	
	/**
	 * This method is used to least satisfaction index  from list of items
	 * 
	 * @return long  
	 */
	public long getLeastSatisfactionIndex(){
		
		
		List<Long> satisfactionIndexes=items.stream().map(Item::getSatisfactionIndex).collect(Collectors.toList());
		Collections.sort(satisfactionIndexes);
		return satisfactionIndexes.stream().findFirst().get();
	}
	
	/**
	 * This method is used to Item from  list of items based on  @param inputParam
	 * 
	 * @param inputParam
	 * @return an instance of Item see{@link Item}  
	 */
	public Item getItem(long inputParam){
		return items.stream().filter(item -> item.getSatisfactionIndex() == inputParam || item.getTimeTaken() == inputParam)
						.findAny().orElse(null);
		
	}
	
	
	  
		 /**
		  * This method is used to find all the indexes of the items given
		  * 
		  * @return list of satisfaction indexes
		  */
		public List<Long> getAllSatisfactionIndex(){
			return items.stream().map(Item::getSatisfactionIndex).collect(Collectors.toList());
		}
		
		 /**
		  * This method is used to find all the time taken of the items given
		  * 
		  * @return list of satisfaction indexes
		  */
		public List<Long> getAllTimeTaken(){
			return items.stream().map(Item::getTimeTaken).collect(Collectors.toList());
		}
		
	
	
	
	public int getNoOfItems(){		
		return items.size();
		
	}





	public Restaurant getRestaurant() {
		return restaurant;
	}





	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}





	public Set<Item> getItems() {
		return items;
	}





	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	
	
	
	

}
