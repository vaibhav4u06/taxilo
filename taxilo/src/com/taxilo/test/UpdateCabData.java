package com.taxilo.test;

import java.io.IOException;

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

import com.mongodb.Mongo;
import com.taxilo.enums.CategoryEnum;
import com.taxilo.pojo.Place;
import com.taxilo.pojo.Point;

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
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		Mongo mongo = new Mongo("localhost");
		Jongo jongo = new Jongo(mongo.getDB("taxilo"));
		 jongoProviderCollection = jongo.getCollection("provider");
		 jongoUserCollection = jongo.getCollection("user");
		 jongoPlaceCollection = jongo.getCollection("place");
		UpdateCabData ucd = new UpdateCabData();
		ucd.updateLocation();
	}

	private void updateLocation() throws ClientProtocolException, IOException, ParseException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get;
		for(String city:cities){
			String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+city+"&sensor=false&key=AIzaSyC_ibzof9E9OS_gzgnXHFiji1g2fqI5pDg";
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
