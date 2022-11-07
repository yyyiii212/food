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
	public FoodMapRes increaseFoodMap(String city, String name) {
		FoodMap foodMap = new FoodMap(city, name);
		foodMap.setScore(0);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(name);
		if (foodMapOp.isPresent()) {
			return new FoodMapRes(foodMap, FoodMapRtnCode.NAME_EXISTED.getMessage());
		}
		foodMapDao.save(foodMap);
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes increaseStore(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		Store store = new Store(storeName, storeFood, foodPrice, foodScore);
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		if (storeOp.isPresent()) {
			return new FoodMapRes(FoodMapRtnCode.STORE_EXISTED.getMessage());
		}
		storeDao.save(store);
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreName(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore() / storeList.size();
			String str = String.valueOf(score);
			String newstr = str.substring(0, 3);
			float saveScore = Float.parseFloat(newstr);
			FoodMap foodmap = foodmapOp.get();
			foodmap.setScore(saveScore);
			foodmap = foodMapDao.save(foodmap);
		}
		return new FoodMapRes(store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMap updateFoodMap(String name, String city) {
		Optional<FoodMap> foodMapOp = foodMapDao.findById(name);
		if (!foodMapOp.isPresent()) {
			return null;
		}
		FoodMap foodMap = foodMapOp.get();
		foodMap.setCity(city);
		foodMapDao.save(foodMap);
		return foodMap;
	}

	@Override
	public Store updateStore(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		if (foodnameId.getStoreFood().isEmpty() || foodnameId.getStoreFood() == null
				|| foodnameId.getStoreName().isEmpty() || foodnameId.getStoreName() == null) {
			return null;
		}
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		if (!storeOp.isPresent()) {
			return null;
		}
		Store store = storeOp.get();
		store.setFoodPrice(foodPrice);
		store.setFoodScore(foodScore);
		storeDao.save(store);
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreName(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore() / storeList.size();
			String str = String.valueOf(score);
			String newstr = str.substring(0, 3);
			float saveScore = Float.parseFloat(newstr);
			FoodMap foodmap = foodmapOp.get();
			foodmap.setScore(saveScore);
			foodmap = foodMapDao.save(foodmap);
		}
		return store;

	}

	@Override
	public FoodMapRes deleteFoodMap(String name) {

		Optional<FoodMap> foodMapOp = foodMapDao.findById(name);
		if (foodMapOp.isPresent()) {
			foodMapDao.deleteById(name);
		}else {
			return null;
		}
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes deleteStore(String storeName, String storeFood) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<Store> storeOp = storeDao.findById(foodnameId);
		if (storeOp.isPresent()) {
			storeDao.deleteById(foodnameId);
		}else {
			return null;
		}
		float score = 0;
		String name = storeName;
		Optional<FoodMap> foodmapOp = foodMapDao.findById(name);
		List<Store> storeList = storeDao.findAllByStoreNameOrderByFoodScoreDesc(storeName);
		for (Store item : storeList) {
			score += item.getFoodScore() / storeList.size();
			String str = String.valueOf(score);
			String newstr = str.substring(0, 3);
			float saveScore = Float.parseFloat(newstr);
			FoodMap foodmap = foodmapOp.get();
			foodmap.setScore(saveScore);
			foodmap = foodMapDao.save(foodmap);
		}
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city, int serch) {
		List<FoodMap> foodMapList = foodMapDao.findAllByCityOrderByScoreDesc(city);
		if (foodMapList.size() == 0 || serch < 0) {
			return null;
		}
		List<String> list = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
			List<Store> storeList = storeDao.findAllByStoreNameOrderByFoodScoreDesc(foodmap.getName());
			for (Store store : storeList) {
				if (foodmap.getName().equals(store.getStoreName())) {
					list.add("┍產: " + foodmap.getName() + ", ┍產蝶基: " + foodmap.getScore() + ", : " + store.getStoreFood() + ", 基: "
							+ store.getFoodPrice() + ", 蝶基: " + store.getFoodScore());
				}
			}
		}
		if (serch >= list.size() || serch == 0) {
			return new FoodMapRes(list);
		}
		List<String> str = list.subList(0, serch);
		return new FoodMapRes(str, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getStoreScore(Integer score) {// 陪ボ掸计
		if(score==null) {
			return null;
		}
		List<FoodMap> foodMapList = foodMapDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<String> list = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
			List<Store> storeList = storeDao.findAllByStoreNameOrderByFoodScoreDesc(foodmap.getName());
			for (Store store : storeList) {
				if (foodmap.getName().equals(store.getStoreName())) {
					list.add("┍產: " + foodmap.getName() + ", ┍產蝶基: " + foodmap.getScore() + ", : "
							+ store.getStoreFood() + ", 基: " + store.getFoodPrice() + ", 蝶基: " + store.getFoodScore());
				}
			}
		}
		return new FoodMapRes(list, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getScoreAndFoodScore(Integer score, Integer foodScore) {
		if(score==null || foodScore == null) {
			return null;
		}
		List<FoodMap> foodMapList = foodMapDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<String> list = new ArrayList<>();
		for (FoodMap foodmap : foodMapList) {
			List<Store> storeList = storeDao.findByStoreNameAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(foodmap.getName(),foodScore);
			for (Store store : storeList) {
				if (foodmap.getName().equals(store.getStoreName())) {
					list.add("┍產: " + foodmap.getName() + ", ┍產蝶基: " + foodmap.getScore() +", : " + store.getStoreFood() + ", 基: "
							+ store.getFoodPrice() + ", 蝶基: " + store.getFoodScore());
				}
			}
		}
		return new FoodMapRes(list, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

}
