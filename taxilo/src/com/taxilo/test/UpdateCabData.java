package com.taxilo.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.taxilo.enums.CategoryEnum;
import com.taxilo.enums.UserRoleEnum;
import com.taxilo.pojo.Car;
import com.taxilo.pojo.Place;
import com.taxilo.pojo.Provider;
import com.taxilo.pojo.User;

public class UpdateCabData {
	
	String[] cities = {"Mumbai","Delhi","Kolkata","Bengaluru","Chennai","Ahmedabad","Hyderabad","Pune","Kanpur","Surat","Jaipur","Lucknow",
			"Nagpur","Indore","Bhopal","Ludhiana","Patna","Vadodra","Thane","Agra","Kalyan","Varanasi","Nashik","Meerut","Faridabad","Haora",
			"Pimpri","Allahabad","Amritsar","Vishakhapatnam","Ghaziabad","Rajkot","Jabalpur","Coimbatore","Madurai","Srinagar","Solapur","Aurangabad",
			"Ranchi","Jodhpur","Gwalior","Vijaywad","Chandigarh","Guwahati","Hubli","Tiruchirappalli","Trivandrum","Mysore","Navi","Jalandhar","Bareilly",
			"Kota","Salem","Aligarh","Bhubaneshwar","Moradabad","Gorakhpur","Rajpur","Bhiwandi","Kochi","Jamshedpur","Bhilai","Amravati","Cuttack","Bikaner",
			"Warangal","Guntur","Bhavnagar","Durgapur","Asansol","Ajmer","Kolhapur","Ulhasnagar","Siliguri","Saharanpur","Dehradun","Jamnagar","Bhatpara",
			"Kozhikode","Ujjain","Gulbarga","Tirunelveli","Malegaon","Akola","Belgaum","Mangalore","Bokaro Steel City","Udaipur","Maheshtala","Jhansi",
			"Gaya","Nellore","Jammu","Jalgaon","Davanagere","Kollam"};
	static MongoCollection jongoProviderCollection;
	static MongoCollection jongoUserCollection;
	static MongoCollection jongoPlaceCollection;
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException, URISyntaxException, NoSuchAlgorithmException {
		Mongo mongo = new Mongo("localhost");
		Jongo jongo = new Jongo(mongo.getDB("taxilo"));
		 jongoProviderCollection = jongo.getCollection("provider");
		 jongoUserCollection = jongo.getCollection("user");
		 jongoPlaceCollection = jongo.getCollection("place");
		UpdateCabData ucd = new UpdateCabData();
	//	ucd.updateLocation();
	//	ucd.updateUser();
		ucd.updateProvider();
	}

	private void updateProvider() {
		List<String> numbers = new ArrayList<String>();
		numbers.add("123");numbers.add("456");numbers.add("789");
		Place place = jongoPlaceCollection.findOne(new BasicDBObject().append("name", "Delhi").toString()).as(Place.class);
		List<String> cities = new ArrayList<String>();
		cities.add(place.getId());
		List<Car> cabs = new ArrayList<Car>();
		cabs.add(getCab("NDLS 0001","ABJ","SUV"));
		cabs.add(getCab("NDLS 0002","ABJ","Sedan"));
		Provider provider1 = getProvider("A1",1,numbers,cities,cabs);
		jongoProviderCollection.save(provider1);
		Provider provider2 = getProvider("A2",2,numbers,cities,cabs);
		jongoProviderCollection.save(provider2);
		Provider provider3 = getProvider("A3",3,numbers,cities,cabs);
		jongoProviderCollection.save(provider3);
	}

	private Provider getProvider(String string, int i, List<String> numbers,
			List<String> cities2, List<Car> cabs) {
		Provider provider = new Provider();
		provider.setProviderName(string);
		provider.setProviderNumber(i);
		provider.setPhoneNumber(numbers);
		provider.setCities(cities2);
		provider.setCabs(cabs);
		return provider;
	}

	private Car getCab(String string, String string2, String string3) {
		Car car = new Car();
		car.setCarNumber(string);
		car.setDriverName(string2);
		car.setCarType(string3);
		return car;
	}

	private void updateUser() throws NoSuchAlgorithmException {
		String[] users = new String[]{"baibhaw.kumar","rahul.nanwani","vineet.sharma","abhayjeet.gupta","shaurya.rawat"};
		String[] userNames = new String[]{"Baibhaw Kumar","Rahul Nanwani","Vineet Sharma","Abhayjeet Gupta","Shaurya Rawat"};
		String[] emailids = new String[]{"vaibhav4u06@gmail.com","r.nanwani@gmail.com","sharma.vineet86@gmail.com","abhayjeetgupta@gmail.com","shaurya.rawat@gmail.com"};
		String[] passwords = new String[]{"baibhawk","rahuln","vineets","abhayg","shauryar"};
		for(int i=0;i<users.length;i++){
			String user = users[i];
			String name = userNames[i];
			String email = emailids[i];
			String password = passwords[i];
			System.out.println(password);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
			 
	        StringBuffer sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
			User userObj = getUserObject(user,name,email,sb.toString());
			jongoUserCollection.save(userObj);
		}
	}

	private User getUserObject(String user, String name, String email,
			String password) {
		User userObj = new User();
		userObj.setUsername(user);
		userObj.setName(name);
		userObj.setEmailId(email);
		userObj.setPassword(password);
		userObj.setLastLogin(new Date());
		userObj.setIsLoggedIn(false);
		userObj.setRole(UserRoleEnum.ADMIN.getRole());
		return userObj;
		
	}

	private void updateLocation() throws ClientProtocolException, IOException, ParseException, URISyntaxException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get;
		for(String city:cities){
			String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+URLEncoder.encode(city,"UTF-8")+"&sensor=false&key=AIzaSyC_ibzof9E9OS_gzgnXHFiji1g2fqI5pDg";
			get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			String responseString = EntityUtils.toString(response.getEntity());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(responseString);
			JSONArray arr = (JSONArray) obj.get("results");
			JSONObject res = (JSONObject) arr.get(0);
			String formattedAddress = res.get("formatted_address").toString();
			Object latitude = ((JSONObject)((JSONObject)res.get("geometry")).get("location")).get("lat");
			Object longitude = ((JSONObject)((JSONObject)res.get("geometry")).get("location")).get("lng");
			Double lat = getDoubleValue(latitude);
			Double lng = getDoubleValue(longitude);
			Place place = getPlaceObject(city,lat,lng,formattedAddress);
			jongoPlaceCollection.save(place);
			
		}
		
	}

	private Double getDoubleValue(Object latitude) {
		if(latitude instanceof Double){
			return (Double)latitude;
		}else if(latitude instanceof Long){
			return ((Long)latitude).doubleValue();
		}
		else{
			return null;
		}
	}

	private Place getPlaceObject(String city, Double latitude, Double longitude, String formattedAddress) {
		Place place = new Place();
		place.setLatitude(latitude);
		place.setLongitude(longitude);
		Double[] point= new Double[2];
		point[0]=longitude;
		point[1] = latitude;
		place.setGeoLocation(point);
		place.setName(city);
		place.setCountryName("India");
		place.setCategory(CategoryEnum.CITY.getCategoryString());
		place.setAddress(formattedAddress);
		return place;
	}

}
