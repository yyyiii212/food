package com.example.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.constants.FoodMapRtnCode;
import com.example.food.entity.FoodMap;
import com.example.food.service.ifs.FoodMapService;
import com.example.food.vo.FoodMapReq;
import com.example.food.vo.FoodMapRes;

@RestController
public class FoodMapController {
	@Autowired
	private FoodMapService foodMapService;

	public FoodMapRes checkParam(FoodMapReq req) {
		FoodMapRes res = new FoodMapRes();
		if (!StringUtils.hasText(req.getFood())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_REQUIRED.getMessage());
		} else if (!StringUtils.hasText(req.getName())) {
			return new FoodMapRes(FoodMapRtnCode.STORE_NAME_REQUIRED.getMessage());
		}
		return null;
	}

	@PostMapping(value = "/api/increaseStore")
	public FoodMapRes increaseStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		}
		if (!StringUtils.hasText(req.getCity())) {
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());
		} else if (req.getPrice() == null || req.getPrice() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.PRICE_REQUIRED.getMessage());
		} else if (req.getScore() == null || req.getScore() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.STORE_SCORE_REQUIRED.getMessage());
		}
		FoodMap foodMap = foodMapService.increaseCity(req.getCity(), req.getName(), req.getFood(), req.getPrice(),
				req.getScore());
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/updateStore")
	public FoodMapRes updateStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		}
		if (req.getPrice() == null || req.getPrice() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.PRICE_REQUIRED.getMessage());
		} else if (req.getScore() == null || req.getScore() <= 0) {
			return new FoodMapRes(FoodMapRtnCode.STORE_SCORE_REQUIRED.getMessage());
		}
		FoodMap foodMap = foodMapService.updateStore(req.getName(), req.getFood(), req.getPrice(), req.getScore());
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/deleteStore")
	public FoodMapRes deleteStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParam(req);
		if (check != null) {
			return check;
		}
		return foodMapService.deleteStore(req.getName(), req.getFood());
	}

	@PostMapping(value = "/api/getCity")
	public FoodMapRes getCity(@RequestBody FoodMapReq req) {
//		if (!StringUtils.hasText(req.getCity())) {
//			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());
//		}
//		FoodMap foodMap = new FoodMap();
		FoodMapRes res = new FoodMapRes();
//		foodMap = foodMapService.getCity(req.getCity());
		return res;
	}

	@PostMapping(value = "/api/getCityAndScore")
	public List<FoodMap> getCityAndScore(String name, Integer score) {

		return null;
	}
}
