package com.taxilo.pojo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Car implements Serializable{
	@JsonProperty("carNumber")
	private String carNumber;
	@JsonProperty("driverName")
	private String driverName;
	@JsonProperty("driverPhoneNumbers")
	private List<String> driverPhoneNumbers;
	@JsonProperty("driverImageUrl")
	private List<String> driverImageUrl;
	@JsonProperty("currentLocation")
	private String currentLocation;
	@JsonProperty("currentPoint")
	private Double[] currentPoint;
	@JsonProperty("carType")
	private String carType;
	@JsonProperty("carModel")
	private String carModel;
	public List<String> getDriverImageUrl() {
		return driverImageUrl;
	}
	public void setDriverImageUrl(List<String> driverImageUrl) {
		this.driverImageUrl = driverImageUrl;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	@JsonProperty("isAvailable")
	private Boolean isAvailable;
	@JsonProperty("isAc")
	private Boolean isAc;
	@JsonProperty("rate")
	private Rate rate;
	
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public List<String> getDriverPhoneNumbers() {
		return driverPhoneNumbers;
	}
	public void setDriverPhoneNumbers(List<String> driverPhoneNumbers) {
		this.driverPhoneNumbers = driverPhoneNumbers;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public Double[] getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(Double[] currentPoint) {
		this.currentPoint = currentPoint;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Boolean getIsAc() {
		return isAc;
	}
	public void setIsAc(Boolean isAc) {
		this.isAc = isAc;
	}
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}

}
