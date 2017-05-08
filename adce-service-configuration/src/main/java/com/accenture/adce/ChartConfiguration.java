package com.accenture.adce;

import java.io.Serializable;

public class ChartConfiguration implements Serializable {
	
	
	private static final long serialVersionUID = 105240933616254964L;
	
	private String chartTitle;	
	private int weigth;
	private int height;
	private Object data;
	
	public String getChartTitle() {
		return chartTitle;
	}
	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
	public int getWeigth() {
		return weigth;
	}
	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ChartConfiguration [chartTitle=" + chartTitle + ", weigth=" + weigth + ", height=" + height + ", data="
				+ data + "]";
	}
	public ChartConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chartTitle == null) ? 0 : chartTitle.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + height;
		result = prime * result + weigth;
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
		ChartConfiguration other = (ChartConfiguration) obj;
		if (chartTitle == null) {
			if (other.chartTitle != null)
				return false;
		} else if (!chartTitle.equals(other.chartTitle))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (height != other.height)
			return false;
		if (weigth != other.weigth)
			return false;
		return true;
	}
	
	
	
	
	
	

}
