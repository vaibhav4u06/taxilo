package com.taxilo.rest.resource;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.taxilo.cache.TaxiloCache;
import com.taxilo.pojo.ResponsePojo;

@Path("/content")
public class ContentResource {

	@GET
	@Produces("application/json")
	@Path("/cities")
	public Response getCities(){
		long start = System.currentTimeMillis();
		ResponsePojo res = new ResponsePojo();
		res.setSuccess(true);
		res.setMessage("");
		res.setData(TaxiloCache.cityCache);
		long end = System.currentTimeMillis();
		res.setTimeInSecs(String.valueOf((end-start)/1000));
		return Response.ok().entity(res).build();
	}
	@GET
	@Produces("application/json")
	@Path("/localities/{cityid}")
	public Response getLocalitiesInCity(@PathParam("cityid") String cityId){
		Map<String,String> localities = TaxiloCache.localityCache.get(cityId);
		long start = System.currentTimeMillis();
		ResponsePojo res = new ResponsePojo();
		res.setSuccess(true);
		res.setMessage("");
		res.setData(localities);
		long end = System.currentTimeMillis();
		res.setTimeInSecs(String.valueOf((end-start)/1000));
		return Response.ok().entity(res).build();
	}
	@GET
	@Produces("application/json")
	@Path("/edges/{source}")
	public Response getValidEdgesForSource(@PathParam("source") String source){
		Map<String,String> localities = TaxiloCache.edgeCache.get(source);
		long start = System.currentTimeMillis();
		ResponsePojo res = new ResponsePojo();
		res.setSuccess(true);
		res.setMessage("");
		res.setData(localities);
		long end = System.currentTimeMillis();
		res.setTimeInSecs(String.valueOf((end-start)/1000));
		return Response.ok().entity(res).build();
	}
	@GET
	@Produces("application/json")
	@Path("/airport/{cityid}")
	public Response getAirportsInCity(@PathParam("cityid") String cityId){
		Map<String,String> airports = TaxiloCache.airportCache.get(cityId);
		long start = System.currentTimeMillis();
		ResponsePojo res = new ResponsePojo();
		res.setSuccess(true);
		res.setMessage("");
		res.setData(airports);
		long end = System.currentTimeMillis();
		res.setTimeInSecs(String.valueOf((end-start)/1000));
		return Response.ok().entity(res).build();
	}
}
