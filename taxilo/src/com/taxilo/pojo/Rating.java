package com.taxilo.pojo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Rating implements Serializable{
	@JsonProperty("punctualityRating")
	private Integer punctualityRating;
	@JsonProperty("cleanlinessRating")
	private Integer cleanlinessRating;
	@JsonProperty("hospitalityRating")
	private Integer hospitalityRating;
	@JsonProperty("userId")
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
