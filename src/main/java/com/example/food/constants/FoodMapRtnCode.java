package com.example.food.constants;

public enum FoodMapRtnCode {
	SUCCESSFUL("200","Successful !"),
	CITY_REQUIRED("400","City cannot be null or empty!!"),
	NAME_REQUIRED("400","Name cannot be null or empty!!"),
	SCORE_REQUIRED("400","Score cannot be null or empty or negative number!!"),
	
	STORE_NAME_REQUIRED("400","Store_Name cannot be null or empty!!"),
	STORE_FOOD_REQUIRED("400","Food cannot be null or empty!!"),
	FOOD_PRICE_REQUIRED("400","Price cannot be null or empty or negative number!!"),
	FOOD_SCORE_REQUIRED("400","Store_Score cannot be null or empty or negative number!!");
	
	private String code;
	
	private String message;
	
	private FoodMapRtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
