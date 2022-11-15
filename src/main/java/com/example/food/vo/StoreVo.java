package com.example.food.vo;

import java.util.List;

import com.example.food.entity.FoodMap;

public class StoreVo {
	private String city;
	
	private String name;
	
	private float score;
	
	private List<FoodMap> foodMapList;
	
	public StoreVo() {
		
	}
	
    public StoreVo(String city, String name, float score, List<FoodMap> foodMapList) {
    	this.city = city;
		this.foodMapList = foodMapList;
		this.name =name;
		this.score =score;

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

	public List<FoodMap> getFoodMapList() {
		return foodMapList;
	}

	public void setFoodMapList(List<FoodMap> foodMapList) {
		this.foodMapList = foodMapList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
}
