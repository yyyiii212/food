package com.example.food.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.constants.FoodMapRtnCode;
import com.example.food.entity.FoodMap;
import com.example.food.entity.FoodNameId;
import com.example.food.entity.Store;
import com.example.food.repository.FoodMapDao;
import com.example.food.repository.StoreDao;
import com.example.food.service.ifs.FoodMapService;
import com.example.food.vo.FoodMapRes;

@Service
public class FoodMapServiceImpl implements FoodMapService{
	@Autowired
	private FoodMapDao foodMapDao;
	@Autowired
	private StoreDao storeDao;

	@Override
	public FoodMap increaseCity(String city, String name, String food, Integer price, Integer score) {
		FoodMap foodMap = new FoodMap(city,name,food,price,score);
		return foodMapDao.save(foodMap);
	}
	
	@Override
	public Store increaseStore(String storeName, String storeFood, Integer price, Integer storeScore) {
		Store store= new Store(storeName,storeFood,price,storeScore); 
		return storeDao.save(store);
	}
	
	@Override
	public FoodMap updateStore(String name, String food, Integer price, Integer score) {
		FoodNameId foodnameId = new FoodNameId(name,food);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodnameId);
		FoodMap foodMap = foodMapOp.get();
		if (foodMapOp.isPresent()) {
			foodMap.setPrice(price);
			foodMap.setScore(score);
			foodMapDao.save(foodMap);
		}
		return foodMap;
	}
	
	@Override
	public FoodMapRes deleteStore(String name, String food) {
		FoodNameId foodnameId = new FoodNameId(name,food);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodnameId);
		if (foodMapOp.isPresent()) {
			foodMapDao.deleteById(foodnameId);
		}
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city,List<String> roleList) {
		FoodMap foodMap = foodMapDao.findByCity(city);
		Set<String> roleSet = new HashSet<>();
		for(String str:roleList) {
			roleSet.add(str);
		}
		String role = foodMap.getName();
		String[] roleArray = role.split(",");
		for(int i= 0;i<roleArray.length;i++) {
			String item = roleArray[i].trim();
			roleSet.add(item);
		}
		foodMap.setName(roleSet.toString());
		return new FoodMapRes(foodMap,FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public List<FoodMap> getCityAndScore(String name, Integer score) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodMap getStoreName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodMap getStoreAndScore(String name, Integer score) {
		// TODO Auto-generated method stub
		return null;
	}

}
