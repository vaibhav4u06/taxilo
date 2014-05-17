package com.taxilo.pojo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusEdge implements Serializable{
	@JsonProperty("source")
	private String source;
	@JsonProperty("destination")
	private String destination;
	@JsonProperty("providers")
	private List<Integer> providers;
	public List<Integer> getProviders() {
		return providers;
	}
	public void setProviders(List<Integer> providers) {
		this.providers = providers;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
