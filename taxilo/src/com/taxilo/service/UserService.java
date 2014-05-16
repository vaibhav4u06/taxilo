package com.taxilo.service;

import java.net.UnknownHostException;

import com.taxilo.pojo.User;

public interface UserService {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id) throws UnknownHostException;
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username)  throws UnknownHostException ;
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	public boolean createUser(User user) throws UnknownHostException;
	/**
	 * 
	 * @param emailId
	 * @return
	 */
	public User getUserByEmailId(String emailId) throws UnknownHostException;
	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean isUserNameValid(String username) throws UnknownHostException;
	/**
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changePassword(String id,String oldPassword,String newPassword) throws UnknownHostException ;
	/**
	 *  
	 * @param username
	 * @param password
	 * @return
	 */
	public String loginByUserName(String username,String password) throws UnknownHostException;
	/**
	 * 
	 * @param emailId
	 * @param password
	 * @return
	 */
	public String loginByEmailId(String emailId,String password) throws UnknownHostException;
	
}
