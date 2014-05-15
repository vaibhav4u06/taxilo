package com.taxilo.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rating {
	@JsonProperty("punctualityRating")
	Integer punctualityRating;
	@JsonProperty("cleanlinessRating")
	Integer cleanlinessRating;
	@JsonProperty("hospitalityRating")
	Integer hospitalityRating;
	public Integer getPunctualityRating() {
		return punctualityRating;
	}
	public void setPunctualityRating(Integer punctualityRating) {
		this.punctualityRating = punctualityRating;
	}
	public Integer getCleanlinessRating() {
		return cleanlinessRating;
	}
	public void setCleanlinessRating(Integer cleanlinessRating) {
		this.cleanlinessRating = cleanlinessRating;
	}
	public Integer getHospitalityRating() {
		return hospitalityRating;
	}
	public void setHospitalityRating(Integer hospitalityRating) {
		this.hospitalityRating = hospitalityRating;
	}

}
