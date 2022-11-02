package com.example.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food.entity.FoodMap;
import com.example.food.entity.FoodNameId;

@Repository
public interface FoodMapDao extends JpaRepository<FoodMap,FoodNameId>{
	public FoodMap findByCity(String city);

}
