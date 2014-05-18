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
import com.taxilo.pojo.Edge;
import com.taxilo.pojo.Place;
import com.taxilo.service.EdgeService;
import com.taxilo.service.PlaceService;

public enum EdgeServiceImpl implements EdgeService{
INSTANCE;

public static final String DB="taxilo";
public static final String BUS_EDGE="edge";

private Mongo mongo;
private Jongo jongo;
private MongoCollection jongoBusEdgeCollection;
private PlaceService placeService;
public PlaceService getPlaceService() {
	if(placeService==null)
		placeService = PlaceServiceImpl.INSTANCE;
	return placeService;
}

public void setPlaceService(PlaceService placeService) {
	this.placeService = placeService;
}

public Mongo getMongo() throws UnknownHostException {
	if(mongo == null)
		mongo = new Mongo("localhost");
	return mongo;
}

public void setMongo(Mongo mongo) {
	this.mongo = mongo;
}

public Jongo getJongo() throws UnknownHostException {
	if(jongo==null)
		jongo = new Jongo(getMongo().getDB(DB));
	return jongo;
}

public void setJongo(Jongo jongo) {
	this.jongo = jongo;
}

public MongoCollection getJongoBusEdgeCollection() throws UnknownHostException {
	if(jongoBusEdgeCollection==null)
		jongoBusEdgeCollection = getJongo().getCollection(BUS_EDGE);
	return jongoBusEdgeCollection;
}

public void setJongoBusEdgeCollection(MongoCollection jongoBusEdgeCollection) {
	this.jongoBusEdgeCollection = jongoBusEdgeCollection;
}

@Override
public List<Place> getValidEdges(String source, String category) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("source", source);
	Iterable<Edge> edges = getJongoBusEdgeCollection().find(getQueryObject(query).toString()).as(Edge.class);
	Iterator<Edge> edgeIterator = edges.iterator();
	List<Place> places = new ArrayList<Place>();
	while(edgeIterator.hasNext()){
		Edge edge = edgeIterator.next();
		String destinationId = edge.getDestination();
		Place place =getPlaceService().getPlaceById(destinationId);
		places.add(place);
	}
	return places;
}

private DBObject getQueryObject(Map<String, String> query) {
	DBObject obj = new BasicDBObject();
	for(Map.Entry<String, String> entry:query.entrySet()){
		if(entry.getKey().equalsIgnoreCase("_id"))
			obj.put(entry.getKey(), new ObjectId(entry.getValue()));
		else
			obj.put(entry.getKey(), entry.getValue());
	}
	return obj;
}

@Override
public List<Integer> getProvidersForEdge(String source, String destination) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("source",source);
	query.put("destination", destination);
	Edge edge = getJongoBusEdgeCollection().findOne(getQueryObject(query).toString()).as(Edge.class);
	return edge.getProviders();
}

@Override
public Edge getEdgeById(String id) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	Edge edge = getJongoBusEdgeCollection().findOne(getQueryObject(query).toString()).as(Edge.class);
	return edge;
}

@Override
public Boolean saveEdge(String source, String destination, Integer provider) throws UnknownHostException {
	Map<String,String> query = new HashMap<String,String>();
	query.put("source", source);
	query.put("destination", destination);
	Edge edge = getJongoBusEdgeCollection().findOne(getQueryObject(query).toString()).as(Edge.class);
	if(edge==null){
		Edge newEdge = new Edge();
		newEdge.setSource(source);
		newEdge.setDestination(destination);
		List<Integer> providers = new ArrayList<Integer>();
		providers.add(provider);
		newEdge.setProviders(providers);
		getJongoBusEdgeCollection().save(newEdge);
	}else{
		edge.getProviders().add(provider);
		getJongoBusEdgeCollection().save(edge);
	}
	return true;
}

	
}
