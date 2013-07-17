/**
 * 
 */
package com.jared.core.util.ldap;


import com.jared.core.util.ldap.exception.InvalidLdapConfigurationException;
import com.jared.core.util.ldap.exception.LdapUserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


/**
 * 查询AD用户/验证账号
 * @author changyong.cai
 */
public class LdapUtil
{
//	static Logger logger = Logger.getLogger(LdapUtil.class);
	static Logger logger = LoggerFactory.getLogger(LdapUtil.class);

	/**
	 * 根据登录名查询
	 * @param username
	 * @return
	 * @throws javax.naming.NamingException
	 * @throws InvalidLdapConfigurationException
	 */
	public static List<NodeAttribute> searchUser(String username) throws NamingException, InvalidLdapConfigurationException
	{
		return searchUser(username, false);
	}

	/**
	 * 根据登录名查询
	 * @param username
	 * @param fuzzy	模糊查询
	 * @return
	 * @throws javax.naming.NamingException
	 * @throws InvalidLdapConfigurationException
	 */
	public static List<NodeAttribute> searchUser(String username, boolean fuzzy) throws NamingException, InvalidLdapConfigurationException
	{
		DirContext context = new InitialDirContext(LdapConfig.getInstance().getAdmin_env());
		if (context == null)
		{
			throw new InvalidLdapConfigurationException();
		}

		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String attrList[] = { "name", "cn", "sn", "givenName", "displayName",
				"company", "department", "sAMAccountName", "mail",
				"distinguishedName", "objectCategory"};
		constraints.setReturningAttributes(attrList);
		String filter = "(&(sAMAccountType=805306368)(!(userAccountControl:1.2.840.113556.1.4.803:=2))(sAMAccountName=" + username + "))";
		if (fuzzy)
		{
			filter = "(&(sAMAccountType=805306368)(!(userAccountControl:1.2.840.113556.1.4.803:=2))(sAMAccountName=*" + username + "*))";
		}
		NamingEnumeration<SearchResult> result = context.search(LdapConfig.getInstance().getBase_dn(), filter, constraints);
		
		return populateSearchResult(result);
	}

	/**
	 * 验证域账户，凭证有效时返回LDAP节点信息
	 * @param username
	 * @param password
	 * @return
	 * @throws LdapUserNotFoundException
	 * @throws InvalidLdapConfigurationException
	 */
	public static NodeAttribute authenticate(String username, String password) throws LdapUserNotFoundException, InvalidLdapConfigurationException
	{
		try
		{
			List<NodeAttribute> list = searchUser(username);
			if (list.isEmpty())
			{
				throw new LdapUserNotFoundException(username);
			}
			NodeAttribute node = list.get(0);
			
			Hashtable<String, String> evn = new Hashtable<String, String>();
			evn.put(Context.AUTHORITATIVE, "true");
			evn.put("com.sun.jndi.ldap.connect.pool", "true");
			evn.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			evn.put(Context.PROVIDER_URL, LdapConfig.getInstance().getUrl());
			evn.put(Context.SECURITY_AUTHENTICATION, "simple");
			evn.put(Context.SECURITY_PRINCIPAL, node.getDistinguishedName());
			evn.put(Context.SECURITY_CREDENTIALS, password);
			evn.put("com.sun.jndi.ldap.connect.timeout", LdapConfig.getInstance().getTimeout());
			DirContext initctx = new InitialDirContext(evn);
//			LdapContext initctx = new InitialLdapContext(evn, null);
			if(initctx != null){
				return node;
			}
		} catch (AuthenticationException e) {
			logger.warn("认证失败"); 
		} catch (NamingException e) {
			logger.warn("", e);
		}
		
		return null;
	}

	private static List<NodeAttribute> populateSearchResult(NamingEnumeration<SearchResult> result) throws NamingException
	{
		List<NodeAttribute> list = new ArrayList<NodeAttribute>();
		if (result != null)
		{
			while (result != null && result.hasMoreElements()) 
			{
				SearchResult searchResult = result.nextElement();

				Attributes attrs = searchResult.getAttributes();
				if (attrs != null)  
				{
					NodeAttribute node = new NodeAttribute();
					List<String> objectClass = new ArrayList<String>();
					List<String> memberOf = new ArrayList<String>();
					List<String> member = new ArrayList<String>();
					for (NamingEnumeration<? extends Attribute> enumeration = attrs.getAll(); enumeration.hasMoreElements();) 
					{
						Attribute attr = (Attribute) enumeration.next();
						if(attr.getID().equals("name"))
						{
							node.setName(attr.get().toString());
						}
						else if(attr.getID().equals("cn"))
						{
							node.setCn(attr.get().toString());
						}
						else if(attr.getID().equals("sn"))
						{
							node.setSn(attr.get().toString());
						}
						else if(attr.getID().equals("givenName"))
						{
							node.setGivenName(attr.get().toString());
						}
						else if(attr.getID().equals("company"))
						{
							node.setCompany(attr.get().toString());
						}
						else if(attr.getID().equals("department"))
						{
							node.setDepartment(attr.get().toString());
						}
						else if(attr.getID().equals("mail"))
						{
							node.setMail(attr.get().toString());
						}
						else if(attr.getID().equals("sAMAccountName"))
						{
							node.setAccountName(attr.get().toString());
						}
						else if(attr.getID().equals("displayName"))
						{
							node.setDisplayName(attr.get().toString());
						}
						else if(attr.getID().equals("description"))
						{
							node.setDescription(attr.get().toString());
						}
						else if(attr.getID().equals("distinguishedName"))
						{
							node.setDistinguishedName(attr.get().toString());
						}
						else if(attr.getID().equals("objectCategory"))
						{
							node.setObjectCategory(attr.get().toString());
						}
//						else if(attr.getID().equals("userCertificate"))
//						{
//							String userCertificate = "";
//							Object binary = attr.get();
//							if(binary != null)
//							{
//			                    byte[] buffer = (byte[]) binary;
//			                    try 
//			                    {
//			                    	CertificateFactory certFact = CertificateFactory.getInstance("X.509");
//				                    ByteArrayInputStream binaryIS = new ByteArrayInputStream(buffer);
//				                    while (binaryIS.available() > 0)
//				                    {
//				                        X509Certificate pem = (X509Certificate) certFact.generateCertificate(binaryIS);
//				                        byte[] dercert = new byte[] {};
//				    					dercert = pem.getEncoded();
//				    					BASE64Encoder encoder = new BASE64Encoder();
//				    					userCertificate = encoder.encode(dercert);
//				    					break;
//				                    }
//								} 
//			                    catch (CertificateException e)
//			                    {
//								}
//							}
//		                    node.setUserCertificate(userCertificate);
//						}
						else if(attr.getID().equals("objectClass"))
						{
							Enumeration vals = attr.getAll();
							while (vals.hasMoreElements()) 
							{
								objectClass.add((String)vals.nextElement());
							}
						}
						else if(attr.getID().equals("member"))
						{
							Enumeration vals = attr.getAll();
							while (vals.hasMoreElements()) 
							{
//								String m = vals.nextElement().toString();
//								int index = m.indexOf(",");
//								if (index != -1)
//								{
//									m = m.substring(3, index);
//								}
//								member.add(m);
								member.add((String)vals.nextElement());
							}
						}
						else if(attr.getID().equals("memberOf"))
						{
							Enumeration vals = attr.getAll();
							while (vals.hasMoreElements()) 
							{
//								String m = vals.nextElement().toString();
//								int index = m.indexOf(",");
//								if (index != -1)
//								{
//									m = m.substring(3, index);
//								}
//								memberOf.add(m);
								memberOf.add((String)vals.nextElement());
							}
						}
						else if(attr.getID().equals("lastLogon"))
						{
							node.setLastLogon(attr.get().toString());
						}
						else if(attr.getID().equals("operatingSystem"))
						{
							node.setOperatingSystem(attr.get().toString());
						}
						else if(attr.getID().equals("operatingSystemServicePack"))
						{
							node.setOperatingSystemServicePack(attr.get().toString());
						}
						else if(attr.getID().equals("operatingSystemVersion"))
						{
							node.setOperatingSystemVersion(attr.get().toString());
						}
						else if(attr.getID().equals("dNSHostName"))
						{
							node.setDNSHostName(attr.get().toString());
						}
					}

					node.setObjectClass(objectClass);
					node.setMemberOf(memberOf);
					node.setMember(member);
					
					list.add(node);
				}
			
			}
		}
		return list;
	}

	public static void main(String[] args)
	{
		String username = "changyong.cai", password = "11111!";
		try
		{
//			NodeAttribute ret = authenticate(username, password);
//			System.out.println(ret == null ? "无效的凭证" : ret.getDepartment() + ":" + ret.getDistinguishedName());
			username = "cai";
			List<NodeAttribute> list = searchUser(username, true);
			System.out.println(list.size());
		}
//		catch (LdapUserNotFoundException e)
//		{
//			System.out.println("不存在的账号");
//		}
		catch (InvalidLdapConfigurationException e)
		{
			System.out.println("LDAP 配置无效！");
		}
		catch (NamingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
