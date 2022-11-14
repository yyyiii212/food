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
	public FoodMapRes increaseStore(String city, String name) {// �s�W
		Store store = new Store(city, name);//�s�WStore����A���J����T
		Optional<Store> storeOp = storeDao.findById(name);//��DB�ݦ��L����
		if (storeOp.isPresent()) {//�p�GDB����ƴN�^�ǰT��
			return new FoodMapRes(store, FoodMapRtnCode.NAME_EXISTED.getMessage());
		}
		storeDao.save(store);//�p�GDB�S���Nsave
		return new FoodMapRes(store, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes increaseFoodMap(String storeName, String storeFood, Integer foodPrice, int foodScore) {
		FoodMap foodMap = new FoodMap(storeName, storeFood, foodPrice, foodScore);
		FoodNameId foodNameId = new FoodNameId(storeName, storeFood);//�]��DB�O��PK
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodNameId);//�ҥH��ID�n2��PK
		if (foodMapOp.isPresent()) {//�p�GDB����ƴN�^�ǰT��
			return new FoodMapRes(FoodMapRtnCode.STORE_FOOD_EXISTED.getMessage());
		}
		foodMapDao.save(foodMap);//�]���n�p�⥭���ȩҥH��save
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreName(storeName);
		for (FoodMap item : foodMapList) {// �p���\�I����������
			score += item.getFoodScore();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());// save��DB(Store)
		return new FoodMapRes(foodMap, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}
	
	// �����������Ȧܤp���I��@��A�M��save��Store
	private Store scaleScoreAndSave(float score, Store store, int foodMapListSize) {
		score = score / foodMapListSize;//�p�⥭��
		// Math.floor(�Odouble���A�n�নfloat)�L����˥h�p���I��X��(10�N�O�p���I��@��)
		store.setScore((float) Math.floor((score * 10.0) / 10.0));
		return storeDao.save(store);
	}

	@Override
	public Store updateStore(String name, String city) {// �ק�
		Optional<Store> storeOp = storeDao.findById(name);
		if (!storeOp.isPresent()) {//�p�GDB�S��ƴN�^��null
			return null;
		}
		Store store = storeOp.get();//�]����PK�O��@����A�ҥH�n��.get()
		store.setCity(city);//�ק���
		storeDao.save(store);//�Asave
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
		foodMapDao.save(foodMap);//�]���n�p�⥭���ȩҥH��save
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
	public FoodMapRes deleteStore(String name) {// �R��
		Optional<Store> storeOp = storeDao.findById(name);//�M��ID(PK)
		if (storeOp.isPresent()) {//DB����ƴN�R���æ^�ǰT��
			storeDao.deleteById(name);
			return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
		}
		return null;//DB�S����ƴN�^��Null
	}

	@Override
	public FoodMapRes deleteFoodMap(String storeName, String storeFood) {
		FoodNameId foodNameId = new FoodNameId(storeName, storeFood);
		Optional<FoodMap> foodMapOp = foodMapDao.findById(foodNameId);
		if (foodMapOp.isPresent()) {//DB����ƴN�R��
			foodMapDao.deleteById(foodNameId);
		} else {
			return null;//DB�S����ƴN�^��Null
		}
		float score = 0;
		Optional<Store> storeOp = storeDao.findById(storeName);
		List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(storeName);
		for (FoodMap item : foodMapList) {// �p���\�I����������
			score += item.getFoodScore();
		}
		scaleScoreAndSave(score, storeOp.get(), foodMapList.size());
		return new FoodMapRes(FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getCity(String city, int search) {
		if (search < 0) {//�p�Gsearch�p��0�N�^��null(���b)
			return null;
		}
		List<Store> storeList = storeDao.findAllByCityOrderByScoreDesc(city);//��X�����è̷ӵ����Ƨ�
		if (storeList.isEmpty()) {//�p�GstoreList�S�F��N�^��null
			return null;
		}
		List<StoreVo> storeVoList = new ArrayList<>();// �s��1��StoreVo�A��storeVoList�h��DB��X��T
		for (Store store : storeList) {// ��storeList��foreach��X��쪺�C��name
			//��X�\�U�W�٨è̷��\�I�����Ƨ�
			List<FoodMap> foodMapList = foodMapDao.findAllByStoreNameOrderByFoodScoreDesc(store.getName());
			StoreVo storeVo = new StoreVo(store.getName(), store.getScore(), foodMapList);// �s�W����s��n��X����T
			storeVoList.add(storeVo);// �A��s�W�������iList��
		}
		if (search >= storeVoList.size() || search == 0) {// search�p�G�j��list���שε���0�N�^�Ǿ��storeVoList
			return new FoodMapRes(storeVoList);
		}
		List<StoreVo> str = storeVoList.subList(0, search);// ��Jsearch(����)����ܸ�T
		return new FoodMapRes(str, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapRes getStoreScore(Integer score) {
		if (score == null) {//�p�Gscore�Onull�N�^��null
			return null;
		}
		//�M���\�U�����j��(����)��J���Ʀr
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<StoreVo> storeVoList = new ArrayList<>();
		//�M���\�U�W�r(�̷��\�I�����Ƨ�)
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
		//�M���\�U�����j��(����)��J���Ʀr�A�M��̷��\�U�����Ƨ�
		List<Store> storeList = storeDao.findByScoreGreaterThanEqualOrderByScoreDesc(score);
		List<String> nameList = new ArrayList<>();
		for (Store store : storeList) {// ����Ʈw�̪�name�M���inameList
			nameList.add(store.getName());
		}
		// foodMapList��Mname(list���A)�H���\�I�����A�M��̷��\�I�����Ƨ�
		List<FoodMap> foodMapList = foodMapDao
				.findByStoreNameInAndFoodScoreGreaterThanEqualOrderByFoodScoreDesc(nameList, foodScore);
		FoodMapRes res = new FoodMapRes();
		res.setFoodMapList(foodMapList);
		res.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}
}
