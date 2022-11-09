package com.example.food.vo;

import java.util.List;

import com.example.food.entity.Store;
import com.example.food.entity.FoodMap;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodMapRes {
	private Store store;
	
	private String name;
	
	private FoodMap foodMap;
	
	private String message;
	
	private List<Store> storeList;
	
	private List<FoodMap> foodMapList;

	private FoodMapRes res;
	
	private List<String> str;
	
	private List<StoreVo> storeVoList;
	
	public FoodMapRes() {
		
	}
	

	
	public FoodMapRes(String message) {
		this.message = message;
	}
	
	public FoodMapRes(List<StoreVo> storeVoList) {
		this.storeVoList = storeVoList;
	}
	
	public FoodMapRes(Store foodmap,String message) {
		this.store = foodmap;
		this.message = message;
	}
	
	public FoodMapRes(List<Store> foodMap,List<FoodMap> storeList,String message) {
		this.storeList = foodMap;
		this.foodMapList = storeList;
		this.message = message;
	}
	
	public FoodMapRes(FoodMapRes res ,String message) {
		this.res = res;
		this.message = message;
	}
	
	public FoodMapRes(FoodMapRes res) {
		this.res = res;
	}
	
	public FoodMapRes(FoodMap store ,String message) {
		this.foodMap = store;
		this.message = message;
	}
	
	public FoodMapRes(FoodMap store) {
		this.foodMap = store;
	}


	public FoodMapRes(List<StoreVo> storeVoList, String message) {
		this.storeVoList = storeVoList;
		this.message = message;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FoodMap getFoodMap() {
		return foodMap;
	}

	public void setFoodMap(FoodMap foodMap) {
		this.foodMap = foodMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public List<FoodMap> getFoodMapList() {
		return foodMapList;
	}

	public void setFoodMapList(List<FoodMap> foodMapList) {
		this.foodMapList = foodMapList;
	}

	public FoodMapRes getRes() {
		return res;
	}

	public void setRes(FoodMapRes res) {
		this.res = res;
	}

	public List<String> getStr() {
		return str;
	}

	public void setStr(List<String> str) {
		this.str = str;
	}

	public List<StoreVo> getStoreVoList() {
		return storeVoList;
	}

	public void setStoreVoList(List<StoreVo> storeVoList) {
		this.storeVoList = storeVoList;
	}
	
}
