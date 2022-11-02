package com.example.food.vo;

public class FoodMapReq {
	private String city;

	private String name;

	private String food;

	private Integer price;

	private Integer score;

	public FoodMapReq() {

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
