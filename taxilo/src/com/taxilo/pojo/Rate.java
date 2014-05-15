package com.taxilo.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rate {
	@JsonProperty("acPerKm")
	Integer acPerKm;
	@JsonProperty("nonAcPerKm")
	Integer nonAcPerKm;
	@JsonProperty("minimumDistance")
	String minimumDistance;
	public Integer getAcPerKm() {
		return acPerKm;
	}
	public void setAcPerKm(Integer acPerKm) {
		this.acPerKm = acPerKm;
	}
	public Integer getNonAcPerKm() {
		return nonAcPerKm;
	}
	public void setNonAcPerKm(Integer nonAcPerKm) {
		this.nonAcPerKm = nonAcPerKm;
	}
	public String getMinimumDistance() {
		return minimumDistance;
	}
	public void setMinimumDistance(String minimumDistance) {
		this.minimumDistance = minimumDistance;
	}
}
