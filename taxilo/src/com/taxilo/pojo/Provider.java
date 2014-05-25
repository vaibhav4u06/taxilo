package com.taxilo.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Provider implements Serializable{

	@Id
	@ObjectId
	private String id;
	@JsonProperty("providerName")
	private String providerName;
	@JsonProperty("providerNumber")
	private Integer providerNumber;
	@JsonProperty("phoneNumbers")
	private List<String> phoneNumber;
	@JsonProperty("cities")
	private List<String> cities;
	@JsonProperty("cabs")
	private List<Car> cabs;
	@JsonProperty("webUrl")
	private String webUrl;
	@JsonProperty("playstoreUrl")
	private String playstoreUrl;
	@JsonProperty("halfday")
	private Boolean halfday;
	@JsonProperty("fullday")
	private Boolean fullday;
	@JsonProperty("outStation")
	private Boolean outStation;
	@JsonProperty("intraCity")
	private Boolean intraCity;
	@JsonProperty("overallRating")
	private Double overallRating;
	@JsonProperty("overallPunctualityRating")
	private Double overallPunctualityRating;
	@JsonProperty("overallCleanlinessRating")
	private Double overallCleanlinessRating;
	@JsonProperty("overallHospitalityRating")
	private Double overallHospitalityRating;
	@JsonProperty("ratings")
	private List<Rating> ratings;
	@JsonProperty("userId")
	private String userId;
	@JsonProperty("edges")
	private List<String> edges;

	public List<String> getEdges() {
		return edges;
	}
	public void setEdges(List<String> edges) {
		this.edges = edges;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getProviderNumber() {
		return providerNumber;
	}
	public void setProviderNumber(Integer providerNumber) {
		this.providerNumber = providerNumber;
	}
	public List<String> getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<String> getCities() {
		return cities;
	}
	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	public List<Car> getCabs() {
		return cabs;
	}
	public void setCabs(List<Car> cabs) {
		this.cabs = cabs;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getPlaystoreUrl() {
		return playstoreUrl;
	}
	public void setPlaystoreUrl(String playstoreUrl) {
		this.playstoreUrl = playstoreUrl;
	}
	public Boolean getHalfday() {
		return halfday;
	}
	public void setHalfday(Boolean halfday) {
		this.halfday = halfday;
	}
	public Boolean getFullday() {
		return fullday;
	}
	public void setFullday(Boolean fullday) {
		this.fullday = fullday;
	}
	public Boolean getOutStation() {
		return outStation;
	}
	public void setOutStation(Boolean outStation) {
		this.outStation = outStation;
	}
	public Boolean getIntraCity() {
		return intraCity;
	}
	public void setIntraCity(Boolean intraCity) {
		this.intraCity = intraCity;
	}
	public Double getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(Double overallRating) {
		this.overallRating = overallRating;
	}
	public Double getOverallPunctualityRating() {
		return overallPunctualityRating;
	}
	public void setOverallPunctualityRating(Double overallPunctualityRating) {
		this.overallPunctualityRating = overallPunctualityRating;
	}
	public Double getOverallCleanlinessRating() {
		return overallCleanlinessRating;
	}
	public void setOverallCleanlinessRating(Double overallCleanlinessRating) {
		this.overallCleanlinessRating = overallCleanlinessRating;
	}
	public Double getOverallHospitalityRating() {
		return overallHospitalityRating;
	}
	public void setOverallHospitalityRating(Double overallHospitalityRating) {
		this.overallHospitalityRating = overallHospitalityRating;
	}
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	public void preSave(Rating rating) {
		if(this.ratings==null){
			this.ratings = new ArrayList<Rating>();
			this.ratings.add(rating);
			reCalculateOverall();
		}else{
			Boolean found=false;
			Rating foundRating=null;
			for(Rating erating:this.ratings){
				if(erating.getUserId().equals(rating.getUserId())){
					found=true;
					foundRating = erating;
					break;
				}
			}
			if(found){
				this.ratings.remove(foundRating); this.ratings.add(rating);
				reCalculateOverall();
			}else{
				int totalNum = this.ratings.size();
				this.overallCleanlinessRating = (this.overallCleanlinessRating*totalNum+rating.getCleanlinessRating())/(totalNum+1);
				this.overallHospitalityRating = (this.overallHospitalityRating*totalNum+rating.getHospitalityRating())/(totalNum+1);
				this.overallPunctualityRating = (this.overallPunctualityRating*totalNum+rating.getPunctualityRating())/(totalNum+1);
				this.overallRating = (this.overallCleanlinessRating+this.overallHospitalityRating+this.overallPunctualityRating)/3;
				this.ratings.add(rating);
			}
		}
	}
	private void reCalculateOverall() {
		Double ocr = 0.0;
		Double ohr = 0.0;
		Double opr = 0.0;
		Double or = 0.0;
		int num = this.ratings.size();
		for(Rating rating:this.ratings){
			ocr+=rating.getCleanlinessRating();
			ohr+=rating.getHospitalityRating();
			opr+=rating.getPunctualityRating();
		}
		ocr/=num;
		opr/=num;
		ohr/=num;
		or = (ocr+opr+ohr)/3;
		this.overallRating = or;
		this.overallCleanlinessRating = ocr;
		this.overallHospitalityRating = ohr;
		this.overallPunctualityRating = opr;
	}
	
	
}
