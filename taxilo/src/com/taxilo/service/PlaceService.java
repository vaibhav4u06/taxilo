package com.taxilo.service;

import java.net.UnknownHostException;
import java.util.List;

import com.taxilo.pojo.Place;

public interface PlaceService {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Place getPlaceById(String id) throws UnknownHostException;
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Place getPlaceByExactName(String name) throws UnknownHostException;
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Place> getPlacesByName(String name) throws UnknownHostException;
	/**
	 * 
	 * @param cityId
	 * @return
	 */
	public List<Place> getPlacesByCityId(String cityId) throws UnknownHostException;
	/**
	 * 
	 * @param longitude
	 * @param latitude
	 * @param cityId
	 * @param radius
	 * @return
	 */
	public List<Place> getNearByPlaces(Double longitude,Double latitude,String cityId,int radius) throws UnknownHostException;
	/**
	 * 
	 * @param place
	 * @return
	 */
	public boolean addAPlace(Place place) throws UnknownHostException;
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Place> getNameRegexSearch(String name) throws UnknownHostException;
	/**
	 * 
	 * @param category
	 * @param cityId
	 * @return
	 * @throws UnknownHostException
	 */
	public List<Place> getPlacesByCategory(String category,String cityId) throws UnknownHostException;
	
	
		
}
