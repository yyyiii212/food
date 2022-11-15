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

	// �P�_�qpostman��J���L��
	public FoodMapRes checkParamStore(FoodMapReq req) {
		if (!StringUtils.hasText(req.getCity())) { // �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());// �^��city���ŦX�n�D
		} else if (!StringUtils.hasText(req.getName())) { // �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());// �^��name���ŦX�n�D
		}
		return null;
	}

	// �P�_�qpostman��J���L��
	public FoodMapRes checkParamFoodMap(FoodMapReq req) {
		if (!StringUtils.hasText(req.getStoreFood())) { // �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_REQUIRED.getMessage());// �^��storeFood���ŦX�n�D
		} else if (!StringUtils.hasText(req.getStoreName())) { // �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.STORE_NAME_REQUIRED.getMessage());// �^��storeName���ŦX�n�D
		}
		return null;
	}

	// �s�WStore
	@PostMapping(value = "/api/increase_store")
	public FoodMapRes increaseStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamStore(req);// �P�_�qpostman��J���L��
		if (check != null) {
			return check;
		}
		FoodMapRes res = foodMapService.increaseStore(req.getCity(), req.getName());// �s�W��J���ȶiDB(Store)
		return new FoodMapRes(res);
	}

	// �s�WFoodMap
	@PostMapping(value = "/api/increase_foodMap")
	public FoodMapRes increaseFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// �P�_�qpostman��J���L��
		if (check != null) {
			return check;
		} else if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) { // �p�G��J��FoodPrice��null�Τp��(����)0�N�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());// �^��food_Price���ŦX�n�D
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() > 6) { // �p�G��J��FoodScore�p��(����)0�Τj��N�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// �^��food_Score���ŦX�n�D
		}

		FoodMapRes res = foodMapService.increaseFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());// �s�W��J���ȶiDB(FoodMap)
		return new FoodMapRes(res);
	}

	// �ק�Store
	@PostMapping(value = "/api/updat_store")
	public FoodMapRes updateStore(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamStore(req);// �P�_�qpostman��J���L��
		if (check != null) {
			return check;
		}

		Store Store = foodMapService.updateStore(req.getName(), req.getCity());// �ק��J���ȶiDB(Store)
		if (Store == null) { // �p�G��J���Ȭ�null�άO�䤣��ȴN�^��null�H�ΰT��
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(Store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// �ק�FoodMap
	@PostMapping(value = "/api/update_foodMap")
	public FoodMapRes updateFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// �P�_�qpostman��J���L��
		if (check != null) {
			return check;
		}
		if (req.getFoodPrice() == null || req.getFoodPrice() <= 0) { // �p�G��J��FoodPrice��null�Τp��(����)0�N�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.FOOD_PRICE_REQUIRED.getMessage());// �^��food_Price���ŦX�n�D
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() > 6) { // �p�G��J��FoodScore�p��(����)0�Τj��N�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// �^��food_Score���ŦX�n�D
		}

		FoodMap foodMap = foodMapService.updateFoodMap(req.getStoreName(), req.getStoreFood(), req.getFoodPrice(),
				req.getFoodScore());// �ק��J���ȶiDB(FoodMap)
		if (foodMap == null) { // �p�GfoodMap�䤣��ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// �R��Store
	@PostMapping(value = "/api/delete_store")
	public FoodMapRes deleteStore(@RequestBody FoodMapReq req) {
		if (!StringUtils.hasText(req.getName())) {// �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.NAME_REQUIRED.getMessage());// �^��name���ŦX�n�D
		}

		FoodMapRes res = foodMapService.deleteStore(req.getName());
		if (res == null) { // �p�G��J���Ȭ�null�άO�䤣��ȴN�^��null�H�ΰT��
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// �R��FoodMap
	@PostMapping(value = "/api/delete_foodMap")
	public FoodMapRes deleteFoodMap(@RequestBody FoodMapReq req) {
		FoodMapRes check = checkParamFoodMap(req);// �P�_�qpostman��J���L��
		if (check != null) {
			return check;
		}

		FoodMapRes res = foodMapService.deleteFoodMap(req.getStoreName(), req.getStoreFood());
		if (res == null) { // �p�G��J���Ȭ�null�άO�䤣��ȴN�^��null�H�ΰT��
			return new FoodMapRes(FoodMapRtnCode.ERROR.getMessage());
		}
		return new FoodMapRes(res, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	// �j�M�����A������j�M����
	@PostMapping(value = "/api/search_city")
	public FoodMapRes searchCity(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchCity(req.getCity(), req.getSearch());
		if (!StringUtils.hasText(req.getCity())) { // �P�_���L��J�ȡA�S����J�ȴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.CITY_REQUIRED.getMessage());// �^��city���ŦX�n�D
		} else if (req.getSearch() < 0) { // �p�G��J���Ʀr�p��0
			return new FoodMapRes(FoodMapRtnCode.SERCH_REQUIRED.getMessage());// �^��search���ŦX�n�D
		}
		return res;
	}

	// �j�M���a����(�̷ӵ����Ƨ�)
	@PostMapping(value = "/api/search_store_score")
	public FoodMapRes searchStoreScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchStoreScore(req.getScore());// �I�sfoodMapService�̪��j�M���a����(�̷ӵ����Ƨ�)
		if (req.getScore() <= 0 || req.getScore() >= 6) { // �p�G��J���Ʀr�p��(����)0�A�άO�j��(����)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// �^��score���ŦX�n�D
		}
		return res;
	}

	// �j�M���a�����H�Ω��a�\�I����(�̷ӵ����Ƨ�)
	@PostMapping(value = "/api/search_store_score_and_food_score")
	public FoodMapRes searchStoreScoreAndFoodScore(@RequestBody FoodMapReq req) {
		FoodMapRes res = foodMapService.searchStoreScoreAndFoodScore(req.getScore(), req.getFoodScore());
		if (req.getScore() <= 0 || req.getScore() >= 6) { // �p�G��J���Ʀr�p��(����)0�A�άO�j��(����)6
			return new FoodMapRes(FoodMapRtnCode.SCORE_REQUIRED.getMessage());// �^��score���ŦX�n�D
		} else if (req.getFoodScore() <= 0 || req.getFoodScore() >= 6) { // �p�G��J���Ʀr�p��(����)0�A�άO�j��(����)6
			return new FoodMapRes(FoodMapRtnCode.FOOD_SCORE_REQUIRED.getMessage());// �^��food_Score���ŦX�n�D
		}
		return res;
	}
}
