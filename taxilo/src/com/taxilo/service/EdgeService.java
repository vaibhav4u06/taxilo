package com.taxilo.service;

import java.net.UnknownHostException;
import java.util.List;

import com.taxilo.pojo.Edge;
import com.taxilo.pojo.Place;

public interface EdgeService {
	/**
	 * 
	 * @param id
	 * @return
	 * @throws UnknownHostException
	 */
	public Edge getEdgeById(String id) throws UnknownHostException;
	
	/**
	 * 
	 * @param source
	 * @param category
	 * @return
	 */
	public List<Place> getValidEdges(String source,String category) throws UnknownHostException;
	/**
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	public List<Integer> getProvidersForEdge(String source,String destination) throws UnknownHostException;
	/**
	 * 
	 * @param source
	 * @param destination
	 * @param provider
	 * @return
	 */
	public Boolean saveEdge(String source,String destination,Integer provider) throws UnknownHostException;
}
