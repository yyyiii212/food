package com.example.food.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapReq {
	private String city;

	private String name;
	
	private float score;
	
	@JsonProperty("store_name")
	private String storeName;
	
	@JsonProperty("store_food")
	private String storeFood;
	
	@JsonProperty("food_price")
	private Integer foodPrice;
	
	@JsonProperty("food_score")
	private float foodScore;

	public FoodMapReq() {

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
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

	public float getFoodScore() {
		return foodScore;
	}

	public void setFoodScore(float foodScore) {
		this.foodScore = foodScore;
	}
	
}
