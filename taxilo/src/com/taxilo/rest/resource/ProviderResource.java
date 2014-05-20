package com.taxilo.rest.resource;

import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.taxilo.pojo.Car;
import com.taxilo.pojo.Provider;
import com.taxilo.pojo.Rating;
import com.taxilo.pojo.ResponsePojo;
import com.taxilo.service.ProviderService;
import com.taxilo.service.impl.ProviderServiceImpl;

@Path("/provider")
public class ProviderResource {
	
	private ProviderService providerService;
	
	public ProviderService getProviderService() {
		if(providerService==null)
			providerService = ProviderServiceImpl.INSTANCE;
		return providerService;
	}

	public void setProviderService(ProviderService providerService) {
		this.providerService = providerService;
	}

	@GET
	@Produces("application/json")
	@Path("/v1/json/{id}")
	public Response getProviderById(@PathParam("id") String id){
		long start = System.currentTimeMillis();
		
		try {
			Provider provider = getProviderService().getProviderById(id);
			if(provider!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(provider);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("Provider not found with this id");
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(res).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/v1/json/providernumber/{number}")
	public Response getProviderByProviderId(@PathParam("id") Integer number){
		long start = System.currentTimeMillis();
		
		try {
			Provider provider = getProviderService().getProviderByProviderId(number);
			if(provider!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(provider);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("Provider not found with this provider number");
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(res).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/v1/json/{id}/ratings")
	public Response getRatingsForProvider(@PathParam("id") String id){
		long start = System.currentTimeMillis();
		
		try {
			List<Rating> ratings = getProviderService().getRatingsForProvider(id);
			if(ratings!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(ratings);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("No Ratings for this provider yet");
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(res).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/v1/json/{id}/cities")
	public Response getCitiesForProvider(@PathParam("id") String id){
		long start = System.currentTimeMillis();
		
		try {
			List<String> cities = getProviderService().getApplicableCitiesForProvider(id);
			if(cities!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(cities);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("No Ratings for this provider yet");
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(res).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@POST
	@Produces("application/json")
	@Path("/v1/json/saveprovider")
	public Response saveProvider(@FormParam("provider") Provider provider){
		long start = System.currentTimeMillis();
		
		try {
			getProviderService().saveProvider(provider);
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(true);
			res.setMessage("");
			res.setData("Saved");
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.ok().entity(res).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@POST
	@Produces("application/json")
	@Path("/v1/json/{id}/addcars")
	public Response addCarsForProvider(@FormParam("cars") List<Car> cars,@PathParam("id") String id){
		long start = System.currentTimeMillis();
		
		try {
			getProviderService().addCarsForProvider(id, cars);
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(true);
			res.setMessage("");
			res.setData("Saved cars");
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.ok().entity(res).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
	
	@POST
	@Produces("application/json")
	@Path("/v1/json/{id}/addrating")
	public Response addRatingsForProvider(@FormParam("rating") Rating rating,@PathParam("id") String id){
		long start = System.currentTimeMillis();
		
		try {
			getProviderService().addRatingForProvider(id, rating);
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(true);
			res.setMessage("");
			res.setData("Saved rating");
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.ok().entity(res).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
	}
}
