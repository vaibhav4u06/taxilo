package com.taxilo.listener;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.taxilo.cache.TaxiloCache;
import com.taxilo.pojo.Place;
import com.taxilo.service.EdgeService;
import com.taxilo.service.PlaceService;
import com.taxilo.service.impl.EdgeServiceImpl;
import com.taxilo.service.impl.PlaceServiceImpl;

public class StartupListener implements ServletContextListener{
	EdgeService edgeService = EdgeServiceImpl.INSTANCE;
	PlaceService placeService = PlaceServiceImpl.INSTANCE;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Context Start");
		try {
			System.out.println("City cache start");
			List<Place> places = placeService.getPlacesByCategory("City", null);
			for(Place place:places){
				TaxiloCache.cityCache.put(place.getName(), place.getId());
			}
			System.out.println("City cache end");
			System.out.println("edge cache start");
			List<String> distinctSources = edgeService.getDistinctSource();
			for(String source:distinctSources){
				 List<Place> edgePlaces = edgeService.getValidEdges(source, null);
				 if(TaxiloCache.edgeCache.containsKey(source)){
					 for(Place place:edgePlaces){
						 TaxiloCache.edgeCache.get(source).put(place.getName(), place.getId());
					 }
				 }else{
					 Map<String,String> validEdges = new HashMap<String,String>();
					 for(Place place:edgePlaces){
						 validEdges.put(place.getName(), place.getId());
					 }
					 TaxiloCache.edgeCache.put(source, validEdges);
				 }
			}
			System.out.println("edge cache end");
			System.out.println("locality cache start");
			for(Map.Entry<String, String> entry:TaxiloCache.cityCache.entrySet()){
				List<Place> locPlaces = placeService.getPlacesByCategory("Locality", entry.getValue());
				Map<String,String> locMap = new HashMap<String,String>();
				for(Place place:locPlaces){
					locMap.put(place.getName(), place.getId());
				}
				TaxiloCache.localityCache.put(entry.getKey(), locMap);
			}
			System.out.println("locality cache end");
			System.out.println(TaxiloCache.cityCache);
			System.out.println(TaxiloCache.edgeCache);
			System.out.println(TaxiloCache.localityCache);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

}
