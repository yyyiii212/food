package com.example.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "foodmap")
public class FoodMap {

	@Column(name = "city")
	private String city;

	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "score")
	private float score;
	

	public FoodMap() {

	}

	public FoodMap(String city, String name) {
		this.city = city;
		this.name = name;
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

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	

}
