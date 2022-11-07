package com.example.food.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapReq {
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("name")
	private String name;
	
	private Integer score;
	
	private int serch;
	
	@JsonProperty("store_name")
	private String storeName;
	
	@JsonProperty("store_food")
	private String storeFood;
	
	@JsonProperty("food_price")
	private Integer foodPrice;
	
	@JsonProperty("food_score")
	private Integer foodScore;

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
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

	public Integer getFoodScore() {
		return foodScore;
	}

	public void setFoodScore(Integer foodScore) {
		this.foodScore = foodScore;
	}

	public int getSerch() {
		return serch;
	}

	public void setSerch(int serch) {
		this.serch = serch;
	}
	
	
}
