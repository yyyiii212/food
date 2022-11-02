package com.example.food.vo;

import com.example.food.entity.FoodMap;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodMapRes {
	private FoodMap foodmap;
	
	private String name;
	
	private String message;
	
	public FoodMapRes() {
		
	}
	
	public FoodMapRes(String message) {
		this.message = message;
	}
	
	public FoodMapRes(FoodMap foodmap,String message) {
		this.foodmap = foodmap;
		this.message = message;
	}

	public FoodMap getFoodmap() {
		return foodmap;
	}

	public void setFoodmap(FoodMap foodmap) {
		this.foodmap = foodmap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
