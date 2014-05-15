package com.taxilo.enums;

public enum CategoryEnum {
	CITY("City"),LOCALITY("Locality"),LANDMARK("Landmark");
	
	private CategoryEnum(String category){
		this.category=category;
	}
	public String getCategoryString() {
		return category;
	}
	private String category;
}
