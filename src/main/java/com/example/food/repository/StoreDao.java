package com.example.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food.entity.FoodNameId;
import com.example.food.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store,FoodNameId>{
	public Store findByStoreNameAndStoreFood(String sotreName,String storeFood);
	
	public List<Store> findAllByStoreName(String storeName);
	
	public List<Store> findAllByStoreNameOrderByFoodScoreDesc(String storeName);
	
	public List<Store> findByStoreName(String storeName);
	
	public List<Store> findByStoreNameAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(String storeName,int foodScore);
		
	public List<Store> findByStoreNameAndFoodScoreGreaterThanEqual(String storeName,float foodScore);
	
	

}
