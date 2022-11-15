package com.example.food;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.food.constants.FoodMapRtnCode;
import com.example.food.entity.FoodMap;
import com.example.food.entity.Store;
import com.example.food.repository.FoodMapDao;
import com.example.food.repository.StoreDao;
import com.example.food.service.ifs.FoodMapService;
import com.example.food.vo.FoodMapRes;

@SpringBootTest
public class FoodMapTest {
	@Autowired
	private FoodMapService foodMapService;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodMapDao foodMapDao;

	@Test
	public void increaseStoreTest() {
		Store store = new Store("Taipei", "HI");
		Assert.isTrue(store.getName() != null, "Name cannot be null !!");
		Assert.isTrue(store.getCity() != null, "City cannot be null !!");
		System.out.println(store);
	}

	@Test
	public void updateStoreTest() {
		Store store = foodMapService.updateStore("HI", "Taipei");
		Assert.isTrue(store.getName() != null, "Name cannot be null !!");
		Assert.isTrue(store.getCity() != null, "City cannot be null !!");
		storeDao.delete(store);
	}

	@Test
	public void deleteStoreTest() {
		FoodMapRes store = foodMapService.deleteStore("HI");
		Assert.isTrue(store.getName() != null, "Name cannot be null !!");
	}

	@Test
	public void increaseFoodMapTest() {
		FoodMap foodMap = new FoodMap("HI", "AAAA", 150, 6);
		Assert.isTrue(foodMap.getStoreName() != null, "StoreName cannot be null !!");
		Assert.isTrue(foodMap.getStoreFood() != null, "StoreFood cannot be null !!");
		Assert.isTrue(foodMap.getFoodPrice() != null && foodMap.getFoodPrice() > 0, "FoodPrice is null!");
		Assert.isTrue(foodMap.getFoodScore() < 6 && foodMap.getFoodScore() > 0, "FoodScore is null!");
		System.out.println("店名: " + foodMap.getStoreName() + ", 食物: " + foodMap.getStoreFood() + ", 價格: "
				+ foodMap.getFoodPrice() + ", 評價: " + foodMap.getFoodScore());
	}

	@Test
	public void updateFoodMapTest() {
		FoodMap foodMap = foodMapService.updateFoodMap("HI", "Taipei", 160, 4);
		Assert.isTrue(foodMap.getStoreName() != null, "StoreName cannot be null !!");
		Assert.isTrue(foodMap.getStoreFood() != null, "StoreFood cannot be null !!");
		Assert.isTrue(foodMap.getFoodPrice() != null && foodMap.getFoodPrice() > 0, "FoodPrice is null!");
		Assert.isTrue(foodMap.getFoodScore() < 6 && foodMap.getFoodScore() > 0, "FoodScore is null!");
		foodMapDao.delete(foodMap);
	}

	@Test
	public void deleteFoodMapTest() {
		FoodMap foodMap = new FoodMap("HI", "AAAA", 150, 6);
		Assert.isTrue(foodMap.getStoreName() != null, "StoreName cannot be null !!");
		Assert.isTrue(foodMap.getStoreFood() != null, "StoreFood cannot be null !!");
//		FoodMapRes res  = foodMapService.deleteFoodMap(foodMap);
	}
	
//--------------------------------------------------------------------------------------------
	
	@Test
	public void getCityTest() {
		String city = "Taipei";
		Integer search = 3;
		Assert.isTrue(city != null, "City cannot be null !!");
		Assert.isTrue(search < 6 && search > 0 && search != null, "Search cannot be null !!");
		FoodMapRes res = foodMapService.searchCity(city, search);
		if (search == 0 || search == null) {
			System.out.println(res.getStoreVoList());
			return;
		}
		System.out.println(res.getStr());
	}

	@Test
	public void getStoreScoreTest() {
		Integer score = 3;
		Assert.isTrue(score < 6 && score > 0 && score != null, "Score cannot be null !!");
		FoodMapRes res = foodMapService.searchStoreScore(score);
		System.out.println(res.getStoreVoList());
	}

	@Test
	public void getScoreAndFoodScoreTest() {
		Integer score = 3;
		Integer foodScore = 3;
		Assert.isTrue(score < 6 && score > 0 && score != null, "Score cannot be null !!");
		Assert.isTrue(foodScore < 6 && foodScore > 0 && foodScore != null, "FoodScore cannot be null !!");
		FoodMapRes res = foodMapService.searchStoreScoreAndFoodScore(score, foodScore);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		System.out.println(res.getFoodMapList()+""+res.getMessage());
	}
}
