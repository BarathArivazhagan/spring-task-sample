  Approach :
  
  
  Step 1 : Load the given data from the file  
  
  Step 2: Find  the satisfication index and  time taken of  all the items 
         Let satisfactionIndexes be satisfaction index of al lthe items
         Let timeTakens be time taken of all the items
         
  Step 3: Form the matrix as per the knapsack algorithm for the minutes entered 
            
   Let enterbed minutes be minutes 
   Let total no of items  be totalNoOfItems
   Let matrix be  matrix[totalNoOfItems + 1][minutes + 1)]
   
   Set all columns at row 0 to be 0 if 
   
   Set the first row to zero if row is less than or equal to total nof of times 
   
   Let initial weight be 1
   
   IF:  current item`s time taken is less than or equal to running weight
    
        Given a weight, check if the value of the current item + value of the item that we could afford with the remaining              weight is greater than the value without the current item itself and find the maixmum out of it 
        
          matrix[item][weight]=Math.max (satisfactionIndexes[item-1]+matrix[item-1][(int) (weight-timeTakens[item-1])],                   matrix[item-1][weight]);
          
  ELSE: If the current item's weight is more than the running weight, just carry forward the value without the current item
        matrix[item][weight]=matrix[item-1][weight];
  
  Step 4: Get the value of matrix[totalNoOfItems] [minutes] to find the maximum satisfaction index achieved for the minutes.
