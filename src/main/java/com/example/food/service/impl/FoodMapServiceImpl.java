package com.example.food.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.constants.FoodMapRtnCode;
import com.example.food.entity.Store;
import com.example.food.entity.FoodNameId;
import com.example.food.entity.FoodMap;
import com.example.food.repository.StoreDao;
import com.example.food.repository.FoodMapDao;
import com.example.food.service.ifs.FoodMapService;
import com.example.food.vo.FoodMapRes;
import com.example.food.vo.StoreVo;

@Service
public class FoodMapServiceImpl implements FoodMapService {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodMapDao foodMapDao;

	@Override
	public FoodMapRes increaseStore(String city, String name) {
		Store store = new Store(city, name);
		Optional<Store> storeOp = storeDao.findById(name);
		if (storeOp.isPresent()) {
			return new FoodMapRes(store, FoodMapRtnCode.NAME_EXISTED.getMessage());
		}
		storeDao.save(store);
		return new FoodMapRes(store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes increaseFoodMap(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodMap foodMap = new FoodMap(storeName, storeFood, foodPrice, foodScore);
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodnameId);
		if (foodMapOp.isPresent()) {
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_EXISTED.getMessage());
		}
		foodMapDao.save(foodMap);
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreName(storeName);
		for (FoodMap item : foodMapList) {
			score += item.getFoodScore();
			
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public Store updateStore(String name, String city) {
		Optional<Store> storeOp = storeDao.findById(name);
		if (!storeOp.isPresent()) {
			return null;
		}
		Store store = storeOp.get();
		store.setCity(city);
		storeDao.save(store);
		return store;
	}

	@Override
	public FoodMap updateFoodMap(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodnameId);
		if (!foodMapOp.isPresent()) {
			return null;
		}
		FoodMap foodMap = foodMapOp.get();
		foodMap.setFoodPrice(foodPrice);
		foodMap.setFoodScore(foodScore);
		foodMapDao.save(foodMap);
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreName(storeName);
		for (FoodMap item : foodMapList) {
			score += item.getFoodScore();
			
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return foodMap;

	}
	
	private Store scaleScoreAndSave(float score, Store store, int foodMapListSize) {
		score = score / foodMapListSize;
//		String str = String.valueOf(score);
//		String newstr = str.substring(0, 3);
//		float saveScore = Float.parseFloat(newstr);
//		Math.floor((score * 10.0) / 10.0); //無條件捨去小數點後幾位
		store.setScore((float)Math.floor((score * 10.0) / 10.0));//math.floor是double型態要轉成float
		return storeDao.save(store);
	}

	@Override
	public FoodMapRes deleteStore(String name) {
		Optional<Store> storeOp = storeDao.findById(name);
		if (storeOp.isPresent()) {
			storeDao.deleteById(name);
		}else {
			return null;
		}
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes deleteFoodMap(String storeName, String storeFood) {
		FoodNameId foodnameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodnameId);
		if (foodMapOp.isPresent()) {
			foodMapDao.deleteById(foodnameId);
		}else {
			return null;
		}
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(storeName);
		for (FoodMap item : foodMapList) {
			score += item.getFoodScore() / foodMapList.size();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city, int search) {
		if(search < 0) {
			return null;
		}
		List<Store> storeList = storeDao.findAllByCityOrderByScoreDesc(city);
		if (storeList.isEmpty()) {
			return null;
		}		
//		List<String> list = new ArrayList<>(); // 原本寫1個list讓他add String後得到要輸出的資訊
		List<StoreVo> storeVoList = new ArrayList<>();//後來新建1個StoreVo，用storeVoList去讓資料庫輸出資訊
		for (Store store : storeList) {//對storeList做foreach抓出找到的每個name
			List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(store.getName());
			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);//新增物件存放要輸出的資訊
			storeVoList.add(storeVo);//再把新增的物件放進List裡
			
//			for (FoodMap foodMap : foodMapList) {
//				if (store.getName().equals(foodMap.getStoreName())) {
//					list.add("店家: " + store.getName() + ", 店家評價: " + store.getScore() + ", 食物: " + foodMap.getStoreFood() + ", 價格: "
//							+ foodMap.getFoodPrice() + ", 評價: " + foodMap.getFoodScore());
//				}
//			}
		}
		if (search >= storeVoList.size() || search == 0) {//search如果大於list長度或等於0就回傳整個list
			return new FoodMapRes(storeVoList);
		}
		List<StoreVo> str = storeVoList.subList(0, search);//輸入筆數後顯示資訊
		return new FoodMapRes(str, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getStoreScore(Integer score) {
		if(score==null) {
			return null;
		}
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<StoreVo> storeVoList = new ArrayList<>();
		for (Store store : storeList) {
			List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(store.getName());
			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);
			storeVoList.add(storeVo);
		}
		return new FoodMapRes(storeVoList, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getScoreAndFoodScore(Integer score, Integer foodScore) {
		if(score==null || foodScore == null) {
			return null;
		}
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<String> nameList = new ArrayList<>();
		for (Store store : storeList) {//撈資料庫裡的name然後放進nameList
			nameList.add(store.getName());
		}
		
		//foodMapList找尋name(list型態)然後依照評價排序
		List<FoodMap> foodMapList = foodMapDao.findByStoreNameInAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(nameList,foodScore);

//		for (Store store : storeList) {
//			List<FoodMap> foodMapList = foodMapDao.findByStoreNameAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(store.getName(),foodScore);
//			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);
//			storeVoList.add(storeVo);
//		}
		FoodMapRes res= new FoodMapRes(); 
		res.setFoodMapList(foodMapList);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
}
