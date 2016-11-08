package com.demo.app;


import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.demo.app.model.Item;
import com.demo.app.model.Restaurant;
/**
 * This is the configuration class to load the data from the class path resource 
 * @author barath
 *
 */
@Configuration
public class AppConfiguration {
	
	private static final Logger logger=LoggerFactory.getLogger(AppConfiguration.class);
	private static final String SPACE_SPLITERATOR=" "; 
	private static int itemNo=1;
	
	@Value("${data.file.location:classpath:data.txt}")
	private String fileLocation=null;
	
	
	
	@Autowired
	private ResourceLoader resourceLoader;	
	
	@Bean
	public Restaurant restaurant(){
		Restaurant restaurant=new  Restaurant();		
		restaurant.setItems(getItems());		
		return restaurant;	
		
	}
	
	/**
	 * This method is used to get set of items processed after loading the data from the file
	 * 
	 * @return set of items see {@link Item}
	 */
	public Set<Item> getItems(){
		
		Set<Item> items=new HashSet<Item>();
		List<String> lines=this.loadDataFromFile();		
		if(lines !=null && !lines.isEmpty()){			
			lines.stream().skip(1).forEach( line ->{
				String[] itemParams=line.split(SPACE_SPLITERATOR);
				Item item=new Item(itemNo++,Integer.parseInt(itemParams[0]),Integer.parseInt(itemParams[1]));
				items.add(item);
			});
		}	
		return items;
		
	}
	
	
	/**
	 * This method loads the data from the file
	 * 
	 * @return list of String
	 */
	public List<String> loadDataFromFile() {
		
		List<String> lines=new ArrayList<String>();	
		if(logger.isDebugEnabled()){
			logger.debug("Loading the file from filePath"+fileLocation);
		}
		  try{	
			  	
		    	Resource resource=resourceLoader.getResource(fileLocation);		    	
		    	InputStream inputStream=resource.getInputStream();		    	
		        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
		        String line;
		        while ((line = bufferReader.readLine()) != null) {
		        	lines.add(line);		           
		       	 }
		        bufferReader.close();
		        inputStream.close();
		    	}catch(IOException e){
		    		logger.error(e.getMessage());
		    		e.printStackTrace();
		    	}
		
	
		
		return lines;
		
		
	}

}
