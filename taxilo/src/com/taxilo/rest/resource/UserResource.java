package com.taxilo.rest.resource;

import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.taxilo.pojo.ResponsePojo;
import com.taxilo.pojo.User;
import com.taxilo.service.UserService;
import com.taxilo.service.impl.UserServiceImpl;

@Path("/user")
public class UserResource {
	
	private UserService userService;
	public UserService getUserService() {
		if(userService==null){
			userService = UserServiceImpl.INSTANCE;
		}
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@GET
	@Produces("application/json")
	@Path("/v1/json/{id}")
	public Response getUserById(@PathParam("id") String id){
		long start = System.currentTimeMillis();
		try {
			User user = getUserService().getUserById(id);
			if(user!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(user);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("User not found with this id");
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
	@Path("/v1/json/username/{username}")
	public Response getUserByUserName(@PathParam("username") String username){
		long start = System.currentTimeMillis();
		try {
			User user = getUserService().getUserByUsername(username);
			if(user!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(user);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("User not found with this username");
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
	@Path("/v1/json/emailid/{emailid}")
	public Response getUserByEmailId(@PathParam("emailid") String emailId){
		long start = System.currentTimeMillis();
		try {
			User user = getUserService().getUserByEmailId(emailId);
			if(user!=null){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(user);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("User not found with this email id");
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
	@Path("/v1/json/isvalidusername/{username}")
	public Response isValidEmailId(@PathParam("username") String username){
		long start = System.currentTimeMillis();
		try {
			Boolean isValid = getUserService().isUserNameValid(username);
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(true);
			res.setMessage("");
			res.setData(isValid);
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.OK).entity(res).build();
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
	@Path("/v1/json/changepassword/{id}")
	public Response changePassword(@PathParam("id") String id,@FormParam("oldpassword") String oldPassword,@FormParam("newpassword") String newPassword){
		long start = System.currentTimeMillis();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(oldPassword.getBytes());
			byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
	        oldPassword = sb.toString();
	        md.update(newPassword.getBytes());
	         byteData = md.digest();
	         sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
	        newPassword = sb.toString();
			Boolean isChanged = getUserService().changePassword(id, oldPassword, newPassword);
			ResponsePojo res = new ResponsePojo();
			if(isChanged){
				res.setSuccess(true);
				res.setMessage("");
			}else{
				res.setSuccess(false);
				res.setMessage("password could not be changes, try later");
			}
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.OK).entity(res).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		} catch (NoSuchAlgorithmException e) {
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
	@Path("/v1/json/login/username")
	public Response loginByUsername(@FormParam("username") String username,@FormParam("password") String password){
		long start = System.currentTimeMillis();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
			password = sb.toString();
	        String id = getUserService().loginByUserName(username, password);
	        if(id!=null){
	        	ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(id);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.OK).entity(res).build();
	        }else{
	        	ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("Username password combination incorrect");
				res.setData(id);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.OK).entity(res).build();
	        }
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		} catch (NoSuchAlgorithmException e) {
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
	@Path("/v1/json/login/emailid")
	public Response loginByEmailId(@FormParam("emailid") String emailId,@FormParam("password") String password){
		long start = System.currentTimeMillis();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
			password = sb.toString();
	        String id = getUserService().loginByEmailId(emailId, password);
	        if(id!=null){
	        	ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(id);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.OK).entity(res).build();
	        }else{
	        	ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("Username password combination incorrect");
				res.setData(id);
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.status(Response.Status.OK).entity(res).build();
	        }
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		} catch (NoSuchAlgorithmException e) {
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
	@Path("/v1/json/new")
	public Response createUser(@FormParam("name") String name,@FormParam("username") String username,@FormParam("password") String password,@FormParam("emailid") String emailId,@FormParam("role") String role
			,@FormParam("dateofbirth") Date dateOfBirth,@FormParam("phonenumber") String phoneNumber){
		long start = System.currentTimeMillis();
		try {
			User user = new User();
			user.setName(name);
			user.setUsername(username);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int j = 0; j < byteData.length; j++) {
	         sb.append(Integer.toString((byteData[j] & 0xff) + 0x100, 16).substring(1));
	        }
			password = sb.toString();
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);
			user.setEmailId(emailId);
			user.setDateOfBirth(dateOfBirth);
			user.setIsLoggedIn(true);
			user.setRole(role);
			Boolean isSaved = getUserService().createUser(user);
			if(isSaved){
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(true);
				res.setMessage("");
				res.setData(user.getId());
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}else{
				ResponsePojo res = new ResponsePojo();
				res.setSuccess(false);
				res.setMessage("User could note be saved");
				long end = System.currentTimeMillis();
				res.setTimeInSecs(String.valueOf((end-start)/1000));
				return Response.ok().entity(res).build();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			ResponsePojo res = new ResponsePojo();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			long end = System.currentTimeMillis();
			res.setTimeInSecs(String.valueOf((end-start)/1000));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		} catch (NoSuchAlgorithmException e) {
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
