package com.taxilo.service;

import java.net.UnknownHostException;
import java.util.List;

import com.taxilo.pojo.Car;
import com.taxilo.pojo.Edge;
import com.taxilo.pojo.Provider;
import com.taxilo.pojo.Rating;

public interface ProviderService {
	public Provider getProviderById(String id)throws UnknownHostException;
	public Provider getProviderByProviderId(Integer providerId)throws UnknownHostException;
	public List<Rating> getRatingsForProvider(String id)throws UnknownHostException;
	public List<Edge> getEdgesForProvider(String id)throws UnknownHostException;
	public List<String> getApplicableCitiesForProvider(String id)throws UnknownHostException;
	public void saveProvider(Provider provider)throws UnknownHostException;
	public void addCarsForProvider(String id,List<Car> cars)throws UnknownHostException;
	public void addRatingForProvider(String id,Rating rating)throws UnknownHostException;
}
