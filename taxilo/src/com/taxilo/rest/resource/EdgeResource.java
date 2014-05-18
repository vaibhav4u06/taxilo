package com.taxilo.rest.resource;

import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.taxilo.pojo.Edge;
import com.taxilo.pojo.Place;
import com.taxilo.pojo.ResponsePojo;
import com.taxilo.service.EdgeService;
import com.taxilo.service.impl.EdgeServiceImpl;

@Path("/edge")
public class EdgeResource {
	private EdgeService edgeService;
	
	public EdgeService getEdgeService() {
		if(edgeService==null)
			edgeService = EdgeServiceImpl.INSTANCE;
		return edgeService;
	}

	public void setEdgeService(EdgeService edgeService) {
		this.edgeService = edgeService;
	}

	@GET
	@Produces("application/json")
	@Path("/v1/json/{id}")
	public Response getEdgeById(@PathParam("id") String id){
		long start = System.currentTimeMillis();
		try {
			Edge edge = getEdgeService().getEdgeById(id);
			if(edge!=null){
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(true);
				pojo.setMessage("");
				pojo.setData(edge);
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(pojo).build();
			}else{
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(false);
				pojo.setMessage("no edge found with this id");
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(pojo).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo pojo = new ResponsePojo();
			pojo.setSuccess(false);
			pojo.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			pojo.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(pojo).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/v1/json/validedges/{id}")
	public Response getValidEdge(@PathParam("id") String id,@QueryParam("category")String category){
		long start = System.currentTimeMillis();
		try {
			List<Place> places = getEdgeService().getValidEdges(id, category);
			if(places!=null){
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(true);
				pojo.setMessage("");
				pojo.setData(places);
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(pojo).build();
			}else{
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(false);
				pojo.setMessage("no edge found with this id");
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(pojo).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo pojo = new ResponsePojo();
			pojo.setSuccess(false);
			pojo.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			pojo.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(pojo).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/v1/json/getproviders")
	public Response getProviders(@QueryParam("source") String source,@QueryParam("destination") String destination){
		long start = System.currentTimeMillis();
		try {
			List<Integer> providers = getEdgeService().getProvidersForEdge(source, destination);
			if(providers!=null){
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(true);
				pojo.setMessage("");
				pojo.setData(providers);
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(pojo).build();
			}else{
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(false);
				pojo.setMessage("no providers for this route");
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(pojo).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo pojo = new ResponsePojo();
			pojo.setSuccess(false);
			pojo.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			pojo.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(pojo).build();
		}
	}
	
	@POST
	@Produces("application/json")
	@Path("/v1/json/save")
	public Response saveEdge(@FormParam("source") String source,@FormParam("destination") String destination,@FormParam("provider") Integer provider){
		long start = System.currentTimeMillis();
		try {
			Boolean isSaved = getEdgeService().saveEdge(source, destination, provider);
			if(isSaved){
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(true);
				pojo.setMessage("");
				pojo.setData("saved");
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(pojo).build();
			}else{
				ResponsePojo pojo = new ResponsePojo();
				pojo.setSuccess(false);
				pojo.setMessage("unable to save");
				long end = System.currentTimeMillis();
				pojo.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.NOT_FOUND).entity(pojo).build();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo pojo = new ResponsePojo();
			pojo.setSuccess(false);
			pojo.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			pojo.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(pojo).build();
		}
	}
}
