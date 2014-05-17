package com.taxilo.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.taxilo.pojo.Place;
import com.taxilo.service.PlaceService;

public enum PlaceServiceImpl implements PlaceService {
INSTANCE;
private static final String DB="taxilo";
private static final String PLACE="place";
private Mongo mongo;
private Jongo jongo;
private MongoCollection jongoPlaceCollection;

public Mongo getMongo() throws UnknownHostException {
	if(mongo==null){
		mongo = new Mongo("localhost");
	}
	return mongo;
}

public void setMongo(Mongo mongo) {
	this.mongo = mongo;
}

public Jongo getJongo() throws UnknownHostException {
	if(jongo==null){
		jongo = new Jongo(getMongo().getDB(DB));
	}
	return jongo;
}

public void setJongo(Jongo jongo) {
	this.jongo = jongo;
}

public MongoCollection getJongoPlaceCollection() throws UnknownHostException {
	if(jongoPlaceCollection==null){
		jongoPlaceCollection = getJongo().getCollection(PLACE);
	}
	return jongoPlaceCollection;
}

public void setJongoPlaceCollection(MongoCollection jongoPlaceCollection) {
	this.jongoPlaceCollection = jongoPlaceCollection;
}

@Override
public Place getPlaceById(String id) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("_id", id);
	Place place = getJongoPlaceCollection().findOne(getQueryObject(query).toString()).as(Place.class);
	return place;
}

private DBObject getQueryObject(Map<String, String> query) {
	DBObject obj = new BasicDBObject();
	for(Map.Entry<String, String> entry:query.entrySet()){
		if(entry.getKey().equalsIgnoreCase("_id")){
			obj.put(entry.getKey(), new ObjectId(entry.getValue()));
		}else{
			obj.put(entry.getKey(), entry.getValue());
		}
	}
	return obj;
}

@Override
public Place getPlaceByExactName(String name) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("name", name);
	Place place = getJongoPlaceCollection().findOne(getQueryObject(query).toString()).as(Place.class);
	return place;
}

@Override
public List<Place> getPlacesByName(String name) {
	Map<String,Object> query = new HashMap<String,Object>();
	DBObject search = new BasicDBObject();
	search.put("$search", name);
	query.put("$text", search);
	DBObject sortObject = new BasicDBObject();
	sortObject.put("textScore", -1);
	Iterable<Place> iPlaces = jongoPlaceCollection.find(getTextQueryObject(query).toString()).sort(sortObject.toString()).as(Place.class);
	Iterator<Place> placeIterator = iPlaces.iterator();
	List<Place> places = new ArrayList<Place>();
	while(placeIterator.hasNext()){
		places.add(placeIterator.next());
	}
	return places;
}

private DBObject getTextQueryObject(Map<String, Object> query) {
	DBObject obj = new BasicDBObject();
	for(Map.Entry<String, Object> entry:query.entrySet()){
		obj.put(entry.getKey(), entry.getValue());
	}
	return obj;
}

@Override
public List<Place> getPlacesByCityId(String cityId) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("cityId", cityId);
	Iterable<Place> iPlaces = getJongoPlaceCollection().find(getQueryObject(query).toString()).as(Place.class);
	Iterator<Place> placesIterator = iPlaces.iterator();
	List<Place> places = new ArrayList<Place>();
	while(placesIterator.hasNext()){
		places.add(placesIterator.next());
	}
	return places;
}

@Override
public List<Place> getNearByPlaces(Double longitude, Double latitude,
		String cityId, int radius) throws UnknownHostException {
	DBObject query = new BasicDBObject();
	Double[] point = new Double[2];
	point[0] = longitude;
	point[1] = latitude;
	DBObject near = new BasicDBObject().append("$near", point).append("$maxDistance", radius/111.12);
	query.put("geoLocation", near);
	query.put("cityId", cityId);
	Iterable<Place> iPlace = getJongoPlaceCollection().find(query.toString()).as(Place.class);
	Iterator<Place> placeIterator = iPlace.iterator();
	List<Place> places = new ArrayList<Place>();
	while(placeIterator.hasNext()){
		places.add(placeIterator.next());
	}
	return places;
}

@Override
public boolean addAPlace(Place place) throws UnknownHostException {
	getJongoPlaceCollection().save(place);
	if(place.getCategory().equalsIgnoreCase("city")){
		place.setCityId(place.getId());
		getJongoPlaceCollection().save(place);
	}
	return true;
}

@Override
public List<Place> getNameRegexSearch(String name) throws UnknownHostException {
	DBObject regex = new BasicDBObject().append("$regex", ".*"+name+".*");
	DBObject query = new BasicDBObject().append("name", regex);
	Iterable<Place> iPlaces = getJongoPlaceCollection().find(query.toString()).as(Place.class);
	Iterator<Place> placesIterator = iPlaces.iterator();
	List<Place> places = new ArrayList<Place>();
	while(placesIterator.hasNext()){
		places.add(placesIterator.next());
	}
	return places;
}
}
