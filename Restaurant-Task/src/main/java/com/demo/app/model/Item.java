package com.demo.app.model;




public class Item {
	
	private long itemNo;
		
	private long satisfactionIndex;
	
	private long timeTaken;
	
	
	
	public Item(long itemNo, long satisfactionIndex, long timeTaken) {
		super();
		this.itemNo = itemNo;
		this.satisfactionIndex = satisfactionIndex;
		this.timeTaken = timeTaken;
		
	}

	

	public long getItemNo() {
		return itemNo;
	}

	public void setItemNo(long itemNo) {
		this.itemNo = itemNo;
	}

	public long getSatisfactionIndex() {
		return satisfactionIndex;
	}

	public void setSatisfactionIndex(long satisfactionIndex) {
		this.satisfactionIndex = satisfactionIndex;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	
	public Item() {
		super();
		
	}

	

	


	@Override
	public String toString() {
		return "Item [itemNo=" + itemNo + ", satisfactionIndex=" + satisfactionIndex + ", timeTaken=" + timeTaken + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemNo ^ (itemNo >>> 32));
		result = prime * result + (int) (satisfactionIndex ^ (satisfactionIndex >>> 32));
		result = prime * result + (int) (timeTaken ^ (timeTaken >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (itemNo != other.itemNo)
			return false;
		if (satisfactionIndex != other.satisfactionIndex)
			return false;
		if (timeTaken != other.timeTaken)
			return false;
		return true;
	}
	
	

	
	
	
	

}
