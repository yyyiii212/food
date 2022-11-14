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
	public FoodMapRes increaseStore(String city, String name) {// 新增
		Store store = new Store(city, name);//新增Store物件，放輸入的資訊
		Optional<Store> storeOp = storeDao.findById(name);//找DB看有無重複
		if (storeOp.isPresent()) {//如果DB有資料就回傳訊息
			return new FoodMapRes(store, FoodMapRtnCode.NAME_EXISTED.getMessage());
		}
		storeDao.save(store);//如果DB沒有就save
		return new FoodMapRes(store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes increaseFoodMap(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodMap foodMap = new FoodMap(storeName, storeFood, foodPrice, foodScore);
		FoodNameId foodNameId = new FoodNameId(storeName, storeFood);//因為DB是雙PK
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodNameId);//所以找ID要2個PK
		if (foodMapOp.isPresent()) {//如果DB有資料就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_EXISTED.getMessage());
		}
		foodMapDao.save(foodMap);//因為要計算平均值所以先save
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreName(storeName);
		for (FoodMap item : foodMapList) {// 計算餐點評價平均值
			score += item.getFoodScore();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());// save給DB(Store)
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}
	
	// 取評價平均值至小數點後一位，然後save給Store
	private Store scaleScoreAndSave(float score, Store store, int foodMapListSize) {
		score = score / foodMapListSize;//計算平均
		// Math.floor(是double型態要轉成float)無條件捨去小數點後幾位(10就是小數點後一位)
		store.setScore((float) Math.floor((score * 10.0) / 10.0));
		return storeDao.save(store);
	}

	@Override
	public Store updateStore(String name, String city) {// 修改
		Optional<Store> storeOp = storeDao.findById(name);
		if (!storeOp.isPresent()) {//如果DB沒資料就回傳null
			return null;
		}
		Store store = storeOp.get();//因為找PK是單一物件，所以要用.get()
		store.setCity(city);//修改資料
		storeDao.save(store);//再save
		return store;
	}

	@Override
	public FoodMap updateFoodMap(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodNameId foodNameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodNameId);
		if (!foodMapOp.isPresent()) {
			return null;
		}
		FoodMap foodMap = foodMapOp.get();
		foodMap.setFoodPrice(foodPrice);
		foodMap.setFoodScore(foodScore);
		foodMapDao.save(foodMap);//因為要計算平均值所以先save
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreName(storeName);
		for (FoodMap item : foodMapList) {
			score += item.getFoodScore();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return foodMap;

	}

	@Override
	public FoodMapRes deleteStore(String name) {// 刪除
		Optional<Store> storeOp = storeDao.findById(name);//尋找ID(PK)
		if (storeOp.isPresent()) {//DB有資料就刪除並回傳訊息
			storeDao.deleteById(name);
			return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
		}
		return null;//DB沒有資料就回傳Null
	}

	@Override
	public FoodMapRes deleteFoodMap(String storeName, String storeFood) {
		FoodNameId foodNameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodNameId);
		if (foodMapOp.isPresent()) {//DB有資料就刪除
			foodMapDao.deleteById(foodNameId);
		} else {
			return null;//DB沒有資料就回傳Null
		}
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(storeName);
		for (FoodMap item : foodMapList) {// 計算餐點評價平均值
			score += item.getFoodScore();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city, int search) {
		if (search < 0) {//如果search小於0就回傳null(防呆)
			return null;
		}
		List<Store> storeList = storeDao.findAllByCityOrderByScoreDesc(city);//找出城市並依照評價排序
		if (storeList.isEmpty()) {//如果storeList沒東西就回傳null
			return null;
		}
		List<StoreVo> storeVoList = new ArrayList<>();// 新建1個StoreVo，用storeVoList去讓DB輸出資訊
		for (Store store : storeList) {// 對storeList做foreach抓出找到的每個name
			//找出餐廳名稱並依照餐點評價排序
			List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(store.getName());
			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);// 新增物件存放要輸出的資訊
			storeVoList.add(storeVo);// 再把新增的物件放進List裡
		}
		if (search >= storeVoList.size() || search == 0) {// search如果大於list長度或等於0就回傳整個storeVoList
			return new FoodMapRes(storeVoList);
		}
		List<StoreVo> str = storeVoList.subList(0, search);// 輸入search(筆數)後顯示資訊
		return new FoodMapRes(str, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getStoreScore(Integer score) {
		if (score == null) {//如果score是null就回傳null
			return null;
		}
		//尋找餐廳評價大於(等於)輸入的數字
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<StoreVo> storeVoList = new ArrayList<>();
		//尋找餐廳名字(依照餐點評價排序)
		for (Store store : storeList) {
			List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(store.getName());
			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);
			storeVoList.add(storeVo);
		}
		return new FoodMapRes(storeVoList, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getScoreAndFoodScore(Integer score, Integer foodScore) {
		if (score == null || foodScore == null) {
			return null;
		}
		//尋找餐廳評價大於(等於)輸入的數字，然後依照餐廳評價排序
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<String> nameList = new ArrayList<>();
		for (Store store : storeList) {// 撈資料庫裡的name然後放進nameList
			nameList.add(store.getName());
		}
		// foodMapList找尋name(list型態)以及餐點評價，然後依照餐點評價排序
		List<FoodMap> foodMapList = foodMapDao
				.findByStoreNameInAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(nameList, foodScore);
		FoodMapRes res = new FoodMapRes();
		res.setFoodMapList(foodMapList);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
}
