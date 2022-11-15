package com.example.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.food.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store,String>{

	public List<Store> findAllByCityOrderByScoreDesc(String city);
	
	public List<Store> findByScoreGreaterThanEqualOrderByScoreDesc(float score);

}
