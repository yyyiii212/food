package com.example.food.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FoodNameId implements Serializable{
	private String name;
	
	private String food;
	
	public FoodNameId() {
		
	}
	
	public FoodNameId(String name,String food) {
		this.food = food;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}
	
}
