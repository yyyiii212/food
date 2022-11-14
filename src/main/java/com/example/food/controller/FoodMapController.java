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

	public FoodMapRes checkParam(FoodMapReq req) {
		if (!StringUtils.hasText(req.getStoreFood())) {//耞Τ礚块
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getStoreName())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_NAME_REQUIRED.getMessage());
		}
		return null;
	}

	public FoodMapRes checkParamFoodMap(FoodMapReq req) {
		if (!StringUtils.hasText(req.getCity())) {//耞Τ礚块
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getName())) {
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());
		}
		return null;
	}

	@PostMapping(value = "/api/increaseFoodMap")
	public FoodMapRes increaseFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);
		if (check != null) {
			return check;
		}
		FoodMapRes res = foodMapService.increaseStore(req.getCity(), req.getName());
		return new FoodMapRes(res);
	}

	@PostMapping(value = "/api/increaseStore")
	public FoodMapRes increaseStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		} else if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() >= 6) {
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());
		}
		FoodMapRes res = foodMapService.increaseFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());
		return new FoodMapRes(res);
	}

	@PostMapping(value = "/api/updateFoodMap")
	public FoodMapRes updateFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);
		if (check != null) {
			return check;
		}
		Store Store = foodMapService.updateStore(req.getName(), req.getCity());
		if (Store == null) {
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(Store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/updateStore")
	public FoodMapRes updateStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		}
		if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() > 6) {
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());
		}
		FoodMap foodMap = foodMapService.updateFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());
		if (foodMap == null) {
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/deleteFoodMap")
	public FoodMapRes deleteFoodMap(@RequestBody FoodMapReq req) {
		if (!StringUtils.hasText(req.getName())) {
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());
		}
		FoodMapRes res = foodMapService.deleteStore(req.getName());
		if (res == null) {
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/deleteStore")
	public FoodMapRes deleteStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		}
		FoodMapRes res = foodMapService.deleteFoodMap(req.getStoreName(), req.getStoreFood());
		if (res == null) {
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/searchCity")
	public FoodMapRes getCity(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.getCity(req.getCity(), req.getSearch());
		if (!StringUtils.hasText(req.getCity())) {//耞Τ礚块
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());
		} else if (req.getSearch() < 0) {// 狦块计0
			return new FoodMapRes(FoodMapRtnCode.SERCH_REQUIRED.getMessage());// 肚searchぃ才璶―
		}
		return res;
	}

	@PostMapping(value = "/api/searchStoreScore")
	public FoodMapRes getStoreScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.getStoreScore(req.getScore());
		if (req.getScore() <= 0 || req.getScore() >= 6) {// 狦块计(单)0┪琌(单)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// 肚scoreぃ才璶―
		}
		return res;
	}

	@PostMapping(value = "/api/searchStoreScoreAndFoodScore")
	public FoodMapRes getScoreAndFoodScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.getScoreAndFoodScore(req.getScore(), req.getFoodScore());
		if (req.getScore() <= 0 || req.getScore() >= 6) {// 狦块计(单)0┪琌(单)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// 肚scoreぃ才璶―
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() >= 6) {//
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());
		}
		return res;
	}
}
