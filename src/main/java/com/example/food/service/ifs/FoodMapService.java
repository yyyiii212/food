package com.example.food.service.ifs;

import java.util.List;

import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	public FoodMap increaseCity(String city,String name,String food,Integer price,Integer score);
	
	public Store increaseStore(String storeName,String storeFood,Integer price,Integer storeScore);
	
	public FoodMap updateStore(String name,String food,Integer price,Integer score); 
	
	public FoodMapRes deleteStore(String name,String food);
	
	public FoodMapRes getCity(String city,List<String> roleList);
	
	public List<FoodMap> getCityAndScore(String name,Integer score);
	
	public FoodMap getStoreName(String name);
	
	public FoodMap getStoreAndScore(String name,Integer score); 
}
