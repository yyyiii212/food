package com.example.food.service.ifs;

import com.example.food.entity.Store;
import com.example.food.entity.FoodMap;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	public FoodMapRes increaseStore(String city,String name);
	
	public FoodMapRes increaseFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore);
	
	public Store updateStore(String name,String city); 
	
	public FoodMap updateFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore); 
	
	public FoodMapRes deleteStore(String name);
	
	public FoodMapRes deleteFoodMap(String storeName,String storeFood);
	
	public FoodMapRes getCity(String city, int serch);
	
	public FoodMapRes getStoreScore(Integer score);
	
	public FoodMapRes getScoreAndFoodScore(Integer score,Integer foodScore);

}
