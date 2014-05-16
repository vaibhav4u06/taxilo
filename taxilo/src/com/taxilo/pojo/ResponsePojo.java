package com.taxilo.pojo;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponsePojo implements Serializable{
	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private Object data;
	@JsonProperty("timeInSecs")
	private String timeInSecs;
	public Boolean getSuccess() {
		return success;
	}
	public String getTimeInSecs() {
		return timeInSecs;
	}
	public void setTimeInSecs(String timeInSecs) {
		this.timeInSecs = timeInSecs;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
