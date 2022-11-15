package com.example.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.constants.FoodMapRtnCode;
import com.example.food.entity.Store;
import com.example.food.entity.FoodMap;
import com.example.food.service.ifs.FoodMapService;
import com.example.food.vo.FoodMapReq;
import com.example.food.vo.FoodMapRes;

@RestController
public class FoodMapController {
	@Autowired
	private FoodMapService foodMapService;

	// 判斷從postman輸入有無值
	public FoodMapRes checkParamStore(FoodMapReq req) {
		if (!StringUtils.hasText(req.getCity())) { // 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());// 回傳city不符合要求
		} else if (!StringUtils.hasText(req.getName())) { // 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());// 回傳name不符合要求
		}
		return null;
	}

	// 判斷從postman輸入有無值
	public FoodMapRes checkParamFoodMap(FoodMapReq req) {
		if (!StringUtils.hasText(req.getStoreFood())) { // 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_REQUIRED.getMessage());// 回傳storeFood不符合要求
		} else if (!StringUtils.hasText(req.getStoreName())) { // 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.STORE_NAME_REQUIRED.getMessage());// 回傳storeName不符合要求
		}
		return null;
	}

	// 新增Store
	@PostMapping(value = "/api/increase_store")
	public FoodMapRes increaseStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamStore(req);// 判斷從postman輸入有無值
		if (check != null) {
			return check;
		}
		FoodMapRes res = foodMapService.increaseStore(req.getCity(), req.getName());// 新增輸入的值進DB(Store)
		return new FoodMapRes(res);
	}

	// 新增FoodMap
	@PostMapping(value = "/api/increase_foodMap")
	public FoodMapRes increaseFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// 判斷從postman輸入有無值
		if (check != null) {
			return check;
		} else if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) { // 如果輸入的FoodPrice為null或小於(等於)0就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());// 回傳food_Price不符合要求
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() > 6) { // 如果輸入的FoodScore小於(等於)0或大於就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// 回傳food_Score不符合要求
		}

		FoodMapRes res = foodMapService.increaseFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());// 新增輸入的值進DB(FoodMap)
		return new FoodMapRes(res);
	}

	// 修改Store
	@PostMapping(value = "/api/updat_store")
	public FoodMapRes updateStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamStore(req);// 判斷從postman輸入有無值
		if (check != null) {
			return check;
		}

		Store Store = foodMapService.updateStore(req.getName(), req.getCity());// 修改輸入的值進DB(Store)
		if (Store == null) { // 如果輸入的值為null或是找不到值就回傳null以及訊息
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(Store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 修改FoodMap
	@PostMapping(value = "/api/update_foodMap")
	public FoodMapRes updateFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// 判斷從postman輸入有無值
		if (check != null) {
			return check;
		}
		if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) { // 如果輸入的FoodPrice為null或小於(等於)0就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());// 回傳food_Price不符合要求
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() > 6) { // 如果輸入的FoodScore小於(等於)0或大於就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// 回傳food_Score不符合要求
		}

		FoodMap foodMap = foodMapService.updateFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());// 修改輸入的值進DB(FoodMap)
		if (foodMap == null) { // 如果foodMap找不到值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 刪除Store
	@PostMapping(value = "/api/delete_store")
	public FoodMapRes deleteStore(@RequestBody FoodMapReq req) {
		if (!StringUtils.hasText(req.getName())) {// 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());// 回傳name不符合要求
		}

		FoodMapRes res = foodMapService.deleteStore(req.getName());
		if (res == null) { // 如果輸入的值為null或是找不到值就回傳null以及訊息
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 刪除FoodMap
	@PostMapping(value = "/api/delete_foodMap")
	public FoodMapRes deleteFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// 判斷從postman輸入有無值
		if (check != null) {
			return check;
		}

		FoodMapRes res = foodMapService.deleteFoodMap(req.getStoreName(), req.getStoreFood());
		if (res == null) { // 如果輸入的值為null或是找不到值就回傳null以及訊息
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 搜尋城市，有限制搜尋筆數
	@PostMapping(value = "/api/search_city")
	public FoodMapRes searchCity(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchCity(req.getCity(), req.getSearch());
		if (!StringUtils.hasText(req.getCity())) { // 判斷有無輸入值，沒有輸入值就回傳訊息
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());// 回傳city不符合要求
		} else if (req.getSearch() < 0) { // 如果輸入的數字小於0
			return new FoodMapRes(FoodMapRtnCode.SERCH_REQUIRED.getMessage());// 回傳search不符合要求
		}
		return res;
	}

	// 搜尋店家評價(依照評價排序)
	@PostMapping(value = "/api/search_store_score")
	public FoodMapRes searchStoreScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchStoreScore(req.getScore());// 呼叫foodMapService裡的搜尋店家評價(依照評價排序)
		if (req.getScore() <= 0 || req.getScore() >= 6) { // 如果輸入的數字小於(等於)0，或是大於(等於)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// 回傳score不符合要求
		}
		return res;
	}

	// 搜尋店家評價以及店家餐點評價(依照評價排序)
	@PostMapping(value = "/api/search_store_score_and_food_score")
	public FoodMapRes searchStoreScoreAndFoodScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchStoreScoreAndFoodScore(req.getScore(), req.getFoodScore());
		if (req.getScore() <= 0 || req.getScore() >= 6) { // 如果輸入的數字小於(等於)0，或是大於(等於)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// 回傳score不符合要求
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() >= 6) { // 如果輸入的數字小於(等於)0，或是大於(等於)6
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// 回傳food_Score不符合要求
		}
		return res;
	}
}
