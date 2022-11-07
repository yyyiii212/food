package com.example.food.constants;

public enum FoodMapRtnCode {
	SUCCESSFUL("200","Successful !"),
	CITY_REQUIRED("400","City cannot be null or empty!!"),
	NAME_REQUIRED("400","Name cannot be null or empty!!"),
	SCORE_REQUIRED("400","Score just can only  1 - 5 !!"),
	NAME_EXISTED("400","Name is existed!!"),
	ERROR("400","Not found !!"),
	STORE_EXISTED("400","Store is existed!!"),
	SERCH_REQUIRED("400","Serch cannot be 0 or out of range !"),
	STORE_NAME_REQUIRED("400","Store_Name cannot be null or empty!!"),
	STORE_FOOD_REQUIRED("400","Food cannot be null or empty!!"),
	FOOD_PRICE_REQUIRED("400","Price cannot be null or empty or negative number!!"),
	FOOD_SCORE_REQUIRED("400","Food_Score just can only  1 - 5 !!");
	
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
