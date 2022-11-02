package com.example.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "foodmap")
@IdClass(value = FoodNameId.class)
public class FoodMap {

	@Column(name = "city")
	private String city;

	@Id
	@Column(name = "name")
	private String name;

	@Id
	@Column(name = "food")
	private String food;

	@Column(name = "price")
	private Integer price;

	@Column(name = "score")
	private Integer score;

	public FoodMap() {

	}

	public FoodMap(String city, String name, String food,Integer price, Integer score) {
		this.city = city;
		this.food = food;
		this.name = name;
		this.price = price;
		this.score = score;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
