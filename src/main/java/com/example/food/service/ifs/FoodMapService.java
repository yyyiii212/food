package com.example.food.service.ifs;

import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	public FoodMapRes increaseFoodMap(String city,String name);
	
	public FoodMapRes increaseStore(String storeName,String storeFood,Integer foodPrice,int foodScore);
	
	public FoodMap updateFoodMap(String name,String city); 
	
	public Store updateStore(String storeName,String storeFood,Integer foodPrice,int foodScore); 
	
	public FoodMapRes deleteFoodMap(String name);
	
	public FoodMapRes deleteStore(String storeName,String storeFood);
	
	public FoodMapRes getCity(String city, int serch);
	
	public FoodMapRes getStoreScore(Integer score);
	
	public FoodMapRes getScoreAndFoodScore(Integer score,Integer foodScore);

}
