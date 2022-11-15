package com.example.food.service.ifs;

import com.example.food.entity.Store;
import com.example.food.entity.FoodMap;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	//新增Store
	public FoodMapRes increaseStore(String city,String name);
	//新增FoodMap
	public FoodMapRes increaseFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore);
	//修改Store
	public Store updateStore(String name,String city); 
	//修改FoodMap
	public FoodMap updateFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore); 
	//刪除Store
	public FoodMapRes deleteStore(String name);
	//刪除FoodMap
	public FoodMapRes deleteFoodMap(String storeName,String storeFood);
	//搜尋城市，有限制搜尋筆數
	public FoodMapRes searchCity(String city, int serch);
	//搜尋店家評價(依照評價排序)
	public FoodMapRes searchStoreScore(Integer score);
	//搜尋店家評價以及店家餐點評價(依照評價排序)
	public FoodMapRes searchStoreScoreAndFoodScore(Integer score,Integer foodScore);

}
