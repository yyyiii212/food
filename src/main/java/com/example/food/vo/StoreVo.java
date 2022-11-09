package com.example.food.vo;

import java.util.List;

import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;

public class StoreVo {
	private String name;
	
	private float score;
	
	private List<FoodMap> foodMapList;
	
	public StoreVo() {
		
	}
	
    public StoreVo(String name, float score, List<FoodMap> foodMapList) {
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

	
}
