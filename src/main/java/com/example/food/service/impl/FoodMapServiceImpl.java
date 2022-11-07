package com.example.food.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class FoodMapServiceImpl implements FoodMapService {
	@Autowired
	private FoodMapDao foodMapDao;
	@Autowired
	private StoreDao storeDao;

	@Override
	public FoodMap increaseFoodMap(String city, String name) {
		FoodMap foodMap = new FoodMap(city, name);
		foodMap.setScore(0);
		return foodMapDao.save(foodMap);
	}

	@Override
	public FoodMapRes increaseStore(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		Store store = new Store(storeName, storeFood, foodPrice, foodScore);
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		if (storeOp.isPresent()) {
			return new FoodMapRes("This FoodName is existed!");
		}
		storeDao.save(store);
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreName(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore();
		}
		score = score / storeList.size();
		FoodMap foodmap = foodmapOp.get();
		foodmap.setScore(score);
		foodmap = foodMapDao.save(foodmap);
		return new FoodMapRes(store);
	}

	@Override
	public FoodMap updateFoodMap(String name, String city) {
		Optional<FoodMap> foodMapOp = foodMapDao.findById(name);
		FoodMap foodMap = foodMapOp.get();
		if (foodMapOp.isPresent()) {
			foodMap.setCity(city);
			foodMapDao.save(foodMap);
		}
		return foodMap;
	}

	@Override
	public Store updateStore(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		Store store = storeOp.get();
		if (storeOp.isPresent()) {
			store.setFoodPrice(foodPrice);
			store.setFoodScore(foodScore);
			storeDao.save(store);
		}
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreName(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore();
		}
		score = (score / storeList.size());
		FoodMap foodmap = foodmapOp.get();
		foodmap.setScore(score);
		foodmap = foodMapDao.save(foodmap);
		return store;

	}

	@Override
	public FoodMapRes deleteFoodMap(String name) {

		Optional<FoodMap> foodMapOp = foodMapDao.findById(name);
		if (foodMapOp.isPresent()) {
			foodMapDao.deleteById(name);
		}
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes deleteStore(String storeName, String storeFood) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		if (storeOp.isPresent()) {
			storeDao.deleteById(foodnameId);
		}
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreName(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore();
		}
		score = (score / storeList.size());
		FoodMap foodmap = foodmapOp.get();
		foodmap.setScore(score);
		foodmap = foodMapDao.save(foodmap);
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city) {
		List<FoodMap> foodMapList = foodMapDao.findAllByCity(city);
		List<Store> store = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
			store.addAll(storeDao.findAllByStoreName(foodmap.getName()));
		}
		return new FoodMapRes(foodMapList, store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getStoreScore(float score) {//限制顯示筆數
		List<FoodMap> foodMapList = foodMapDao.findTop3ByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<Store> store = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
			if (foodmap.getScore() >= score) {
				store.addAll(
						storeDao.findByStoreNameAndFoodScoreGreaterThanEqual(foodmap.getName(), foodmap.getScore()));
			}
		}
		return new FoodMapRes(foodMapList, store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getScoreAndFoodScore(float score, int foodScore) {
		List<FoodMap> foodMapList = foodMapDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<Store> store = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
				store.addAll(
						storeDao.findByStoreNameAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(foodmap.getName(),foodScore));
		}
		return new FoodMapRes(foodMapList, store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

}
