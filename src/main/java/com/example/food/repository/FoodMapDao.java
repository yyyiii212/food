package com.example.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food.entity.FoodNameId;
import com.example.food.entity.FoodMap;

@Repository
public interface FoodMapDao extends JpaRepository<FoodMap,FoodNameId>{
	public FoodMap findByStoreNameAndStoreFood(String sotreName,String storeFood);
	
	public List<FoodMap> findAllByStoreName(String storeName);
	
	public List<FoodMap> findAllByStoreNameOrderByFoodScoreDesc(String storeName);
	
	public List<FoodMap> findByStoreNameInAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(List<String> storeNameList, int foodScore);

}
