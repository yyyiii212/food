package com.example.food.service.ifs;

import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	public FoodMap increaseFoodMap(String city,String name);
	
	public FoodMapRes increaseStore(String storeName,String storeFood,Integer foodPrice,float foodScore);
	
	public FoodMap updateFoodMap(String name,String city); 
	
	public Store updateStore(String storeName,String storeFood,Integer foodPrice,float foodScore); 
	
	public FoodMapRes deleteFoodMap(String name);
	
	public FoodMapRes deleteStore(String storeName,String storeFood);
	
	public FoodMapRes getCity(String city);
	
	public FoodMapRes getStoreScore(float score);
	
	public FoodMapRes getScoreAndFoodScore(float score,float foodScore); 
}
