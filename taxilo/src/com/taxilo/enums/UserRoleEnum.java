package com.taxilo.enums;


public enum UserRoleEnum {
	ADMIN("Admin"),PROVIDER("Provider"),USER("User");
	
	private String role;
	
	private UserRoleEnum(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
}
