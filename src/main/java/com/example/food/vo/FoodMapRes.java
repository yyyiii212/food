package com.example.food.vo;

import java.util.List;

import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodMapRes {
	private FoodMap foodmap;
	
	private String name;
	
	private Store store;
	
	private String message;
	
	private List<FoodMap> foodMap;
	
	private List<Store> storeList;

	private FoodMapRes res;
	
	public FoodMapRes() {
		
	}
	
	public FoodMapRes(String message) {
		this.message = message;
	}
	
	public FoodMapRes(List<FoodMap> foodMap) {
		this.foodMap = foodMap;
	}
	
	public FoodMapRes(FoodMap foodmap,String message) {
		this.foodmap = foodmap;
		this.message = message;
	}
	
	public FoodMapRes(List<FoodMap> foodMap,String message) {
		this.foodMap = foodMap;
		this.message = message;
	}
	
	public FoodMapRes(List<FoodMap> foodMap,List<Store> storeList,String message) {
		this.foodMap = foodMap;
		this.storeList = storeList;
		this.message = message;
	}
	
	public FoodMapRes(FoodMapRes res ,String message) {
		this.res = res;
		this.message = message;
	}
	
	public FoodMapRes(Store store ,String message) {
		this.store = store;
		this.message = message;
	}
	
	public FoodMapRes(Store store) {
		this.store = store;
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<FoodMap> getFoodMap() {
		return foodMap;
	}

	public void setFoodMap(List<FoodMap> foodMap) {
		this.foodMap = foodMap;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public FoodMapRes getRes() {
		return res;
	}

	public void setRes(FoodMapRes res) {
		this.res = res;
	}
	
}
