/**
 * 
 */
package com.jared.core.util.ldap.exception;

/**
 * @author changyong.cai
 *
 */
public class LdapUserNotFoundException extends Exception
{
	String username;

	/**
	 * @param username
	 */
	public LdapUserNotFoundException(String username)
	{
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
	
}
