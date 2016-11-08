package com.demo.app;

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
	    	long[][] matrix = new long[totalNoOfItems + 1][(int) (enteredMinutes + 1)];	      
	        for (int col = 0; col <= enteredMinutes; col++) {
	        	matrix[0][col] = 0;	        }
	        
	        for (int row = 0; row <= totalNoOfItems; row++) {
	        	matrix[row][0] = 0;
	        }
	        for (int item=1;item<=totalNoOfItems;item++){
	          
	            for (int weight=1;weight<=enteredMinutes;weight++){
	              
	                if (timeTakens[item-1]<=weight){
	                  
	                	matrix[item][weight]=Math.max (satisfactionIndexes[item-1]+matrix[item-1][(int) (weight-timeTakens[item-1])], matrix[item-1][weight]);
	                }
	                else {
	                   
	                	matrix[item][weight]=matrix[item-1][weight];
	                }
	            }
	        }

	        return matrix[totalNoOfItems][ (int) enteredMinutes];
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
