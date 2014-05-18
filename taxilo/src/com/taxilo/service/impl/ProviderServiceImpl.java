package com.taxilo.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.taxilo.pojo.Car;
import com.taxilo.pojo.Edge;
import com.taxilo.pojo.Provider;
import com.taxilo.pojo.Rating;
import com.taxilo.service.ProviderService;

public enum ProviderServiceImpl implements ProviderService {
	INSTANCE;
	public static final String DB = "taxilo";
	public static final String PROVIDER = "provider";
	
	private Mongo mongo;
	private Jongo jongo;
	private MongoCollection jongoProviderCollection;
	
	public Mongo getMongo() throws UnknownHostException {
		if(mongo==null)
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

	public MongoCollection getJongoProviderCollection() throws UnknownHostException {
		if(jongoProviderCollection==null)
			jongoProviderCollection = getJongo().getCollection(PROVIDER);
		return jongoProviderCollection;
	}

	public void setJongoProviderCollection(MongoCollection jongoProviderCollection) {
		this.jongoProviderCollection = jongoProviderCollection;
	}

	@Override
	public Provider getProviderById(String id) throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("_id", id);
		Provider provider  = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		return provider;
	}

	private DBObject getQueryObject(Map<String, Object> query) {
		DBObject obj = new BasicDBObject();
		for(Map.Entry<String, Object> entry:query.entrySet()){
			if(entry.getKey().equalsIgnoreCase("_id"))
				obj.put(entry.getKey(), new ObjectId(entry.getValue().toString()));
			else
				obj.put(entry.getKey(), entry.getValue());
		}
		return obj;
	}

	@Override
	public Provider getProviderByProviderId(Integer providerId)
			throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("providerNumber", providerId);
		Provider provider = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		return provider;
	}

	@Override
	public List<Rating> getRatingsForProvider(String id)
			throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("_id", id);
		Provider provider = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		if(provider!=null){
			List<Rating> ratings = provider.getRatings();
			return ratings;
		}
		return null;
	}

	@Override
	public List<Edge> getEdgesForProvider(String id)
			throws UnknownHostException {
		
		return null;
	}

	@Override
	public List<String> getApplicableCitiesForProvider(String id)
			throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("_id", id);
		Provider provider = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		if(provider!=null){
			List<String> cities = provider.getCities();
			return cities;
		}
		return null;
	}

	@Override
	public void saveProvider(Provider provider) throws UnknownHostException {
		getJongoProviderCollection().save(provider);
	}

	@Override
	public void addCarsForProvider(String id, List<Car> cars)
			throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("_id", id);
		Provider provider = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		if(provider.getCabs()!=null)
			provider.getCabs().addAll(cars);
		else{
			List<Car> cabs = provider.getCabs();
			cabs =  new ArrayList<Car>();
			cabs.addAll(cars);
		}
		saveProvider(provider);
	}

	@Override
	public void addRatingForProvider(String id, Rating rating)
			throws UnknownHostException {
		Map<String,Object> query = new HashMap<String,Object>();
		query.put("_id", id);
		Provider provider = getJongoProviderCollection().findOne(getQueryObject(query).toString()).as(Provider.class);
		provider.preSave(rating);
		saveProvider(provider);
		
	}

}
