package com.example.food.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FoodNameId implements Serializable{
	private String storeName;
	
	private String storeFood;
	
	public FoodNameId() {
		
	}
	
	public FoodNameId(String storeName,String storeFood) {
		this.storeFood = storeFood;
		this.storeName = storeName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreFood() {
		return storeFood;
	}

	public void setStoreFood(String storeFood) {
		this.storeFood = storeFood;
	}
}
