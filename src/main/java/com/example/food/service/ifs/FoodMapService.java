package com.example.food.service.ifs;

import com.example.food.entity.Store;
import com.example.food.entity.FoodMap;
import com.example.food.vo.FoodMapRes;

public interface FoodMapService {
	//�s�WStore
	public FoodMapRes increaseStore(String city,String name);
	//�s�WFoodMap
	public FoodMapRes increaseFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore);
	//�ק�Store
	public Store updateStore(String name,String city); 
	//�ק�FoodMap
	public FoodMap updateFoodMap(String storeName,String storeFood,Integer foodPrice,int foodScore); 
	//�R��Store
	public FoodMapRes deleteStore(String name);
	//�R��FoodMap
	public FoodMapRes deleteFoodMap(String storeName,String storeFood);
	//�j�M�����A������j�M����
	public FoodMapRes searchCity(String city, int serch);
	//�j�M���a����(�̷ӵ����Ƨ�)
	public FoodMapRes searchStoreScore(Integer score);
	//�j�M���a�����H�Ω��a�\�I����(�̷ӵ����Ƨ�)
	public FoodMapRes searchStoreScoreAndFoodScore(Integer score,Integer foodScore);

}
