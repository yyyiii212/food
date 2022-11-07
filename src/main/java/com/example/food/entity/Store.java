package com.example.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "store")
@IdClass(value = FoodNameId.class)
public class Store {
	@Id
	@Column(name = "store_name")
	private String storeName;

	@Id
	@Column(name = "store_food")
	private String storeFood;

	@Column(name = "food_price")
	private Integer foodPrice;

	@Column(name = "food_score")
	private int foodScore;
	
	public Store() {
		
	}
	
	public Store(String storeName,String storeFood,Integer foodPrice,int foodScore) {
		this.storeFood = storeFood;
		this.storeName = storeName;
		this.foodPrice = foodPrice;
		this.foodScore = foodScore;
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

	public Integer getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(Integer foodPrice) {
		this.foodPrice = foodPrice;
	}

	public int getFoodScore() {
		return foodScore;
	}

	public void setFoodScore(int foodScore) {
		this.foodScore = foodScore;
	}
	
}
