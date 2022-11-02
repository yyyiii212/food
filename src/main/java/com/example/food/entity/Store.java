package com.example.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public class Store {
	@Id
	@Column(name = "store_name")
	private String storeName;

	@Id
	@Column(name = "store_food")
	private String storeFood;

	@Column(name = "price")
	private Integer price;

	@Column(name = "store_score")
	private Integer storeScore;
	
	public Store() {
		
	}
	
	public Store(String storeName,String storeFood,Integer price,Integer storeScore) {
		this.storeFood = storeFood;
		this.storeName = storeName;
		this.price = price;
		this.storeScore = storeScore;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreFood() {
		return storeFood;
	}

	public void setStoreFood(String storeFood) {
		this.storeFood = storeFood;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStoreScore() {
		return storeScore;
	}

	public void setStoreScore(Integer storeScore) {
		this.storeScore = storeScore;
	}
	
}
