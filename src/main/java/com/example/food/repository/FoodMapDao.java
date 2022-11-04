package com.example.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food.entity.FoodMap;

@Repository
public interface FoodMapDao extends JpaRepository<FoodMap,String>{
	public FoodMap findByCity(String city);

	public List<FoodMap> findAllByCity(String city);
	
	public List<FoodMap> findTop3ByScoreGreaterThanEqualOrderByScoreDesc(float score);
	
	public List<FoodMap> findByScoreGreaterThanEqualOrderByScoreDesc(float score);

}
