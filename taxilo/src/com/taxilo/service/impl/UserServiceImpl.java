package com.taxilo.service.impl;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.taxilo.pojo.User;
import com.taxilo.service.UserService;

public enum UserServiceImpl implements UserService {
INSTANCE;
	public static String DB="taxilo";
	public static String USER="user";
	private Mongo mongo;
	private Jongo jongo;
	private MongoCollection jongoUserCollection;
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

	public MongoCollection getJongoUserCollection() throws UnknownHostException {
		if(jongoUserCollection==null){
			jongoUserCollection = getJongo().getCollection(USER);
		}
		return jongoUserCollection;
	}

	public void setJongoUserCollection(MongoCollection jongoUserCollection) {
		this.jongoUserCollection = jongoUserCollection;
	}

	@Override
	public User getUserById(String id) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("_id", id);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		return user;
	}

	private String getQueryObject(Map<String, String> query) {
		DBObject obj = new BasicDBObject();
		for(Map.Entry<String,String> entry:query.entrySet()){
			if(entry.getKey().equalsIgnoreCase("_id")){
				obj.put(entry.getKey(), new ObjectId(entry.getValue()));
			}else{
				obj.put(entry.getKey(), entry.getValue());
			}
			
		}
		return obj.toString();
	}

	@Override
	public User getUserByUsername(String username) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("username", username);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		return user;
	}

	@Override
	public boolean createUser(User user) throws UnknownHostException {
		getJongoUserCollection().save(user);
		return true;
	}

	@Override
	public boolean isUserNameValid(String username) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("username", username);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		if(user==null){
			return true; 
		}else{
			return false;
		}
	}

	@Override
	public boolean changePassword(String id, String oldPassword,
			String newPassword) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("_id", id);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		if(user.getPassword().equals(oldPassword)){
			user.setPassword(newPassword);
			getJongoUserCollection().save(user);
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public String loginByUserName(String username, String password) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("username", username);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		if(user.getPassword().equals(password)){
			user.setLastLogin(new Date());
			user.setIsLoggedIn(true);
			getJongoUserCollection().save(user);
			return user.getId();
		}else{
			return null;
		}
	}
	
	public UserServiceImpl getInstance(){
		return UserServiceImpl.INSTANCE;
	}

	@Override
	public User getUserByEmailId(String emailId) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("emailId", emailId);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		return user;
	}

	@Override
	public String loginByEmailId(String emailId, String password) throws UnknownHostException {
		Map<String,String> query = new HashMap<String,String>();
		query.put("emailId", emailId);
		User user = getJongoUserCollection().findOne(getQueryObject(query)).as(User.class);
		if(user.getPassword().equals(password)){
			user.setLastLogin(new Date());
			user.setIsLoggedIn(true);
			getJongoUserCollection().save(user);
			return user.getId();
		}else{
			return null;
		}
	}
}
