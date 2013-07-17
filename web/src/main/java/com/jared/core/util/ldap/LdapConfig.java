/**
 * 
 */
package com.jared.core.util.ldap;


import com.jared.core.util.ldap.exception.InvalidLdapConfigurationException;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @author changyong.cai
 *
 */
public class LdapConfig
{
    public final static String LDAP_CONFIG_FILE = "ldap.properties"; 
    private String url;
    private String admin_dn;
    private String admin_pwd;
    private String timeout = "1000";
    private String base_dn;

    private Hashtable<String, String> admin_env = new Hashtable<String, String>();
    private boolean valid = false;
    
    private static LdapConfig instance = null;
    /**
	 * 
	 */
	private LdapConfig()
	{
		load();
	}
	
	/**
	 * @return the instance
	 * @throws InvalidLdapConfigurationException 
	 */
	public static LdapConfig getInstance() throws InvalidLdapConfigurationException
	{
		if (instance == null)
		{
			synchronized (LdapConfig.class)
			{
				if (instance == null)
					instance = new LdapConfig();
			}
		}
		if (!instance.isValid())
		{
			throw new InvalidLdapConfigurationException();
		}
		return instance;
	}
    
	/**
	 * @return the valid
	 */
	public boolean isValid()
	{
		return valid;
	}
    
    private synchronized void load()
	{
    	if (valid)
		{
			return;
		}
    	
    	try
		{
            Properties properties = new Properties();
            URL configUrl = LdapConfig.class.getClassLoader().getResource(LDAP_CONFIG_FILE);
            InputStream in = configUrl.openStream();
            properties.load(in);

            url = properties.getProperty("LDAP_URL");
            admin_dn = properties.getProperty("ADMIN_DN");
            admin_pwd = properties.getProperty("ADMIN_PWD");
            timeout = properties.getProperty("TIMEOUT");
            base_dn = properties.getProperty("BASE_DN");

            // 
            admin_env.put(Context.PROVIDER_URL, url);
            admin_env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            admin_env.put(Context.AUTHORITATIVE, "true");
            admin_env.put(Context.SECURITY_AUTHENTICATION, "simple");
            admin_env.put(Context.SECURITY_PRINCIPAL, admin_dn);
            admin_env.put(Context.SECURITY_CREDENTIALS, admin_pwd);
            admin_env.put("com.sun.jndi.ldap.connect.pool", "true");
            admin_env.put("com.sun.jndi.ldap.connect.timeout", timeout);

            // test
			DirContext context = new InitialDirContext(admin_env);
			if (context != null)
			{
				context.close();
			}
			valid = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			valid = false;
		}
    
	}
    
    /**
     * 
     */
    public static void reload()
    {
    	if (instance != null)
		{
			synchronized (instance)
			{
				instance = null;
			}
		}
    }

	public String getUrl()
	{
		return url;
	}

	public String getAdmin_dn()
	{
		return admin_dn;
	}

	public String getAdmin_pwd()
	{
		return admin_pwd;
	}

	public String getTimeout()
	{
		return timeout;
	}

	public String getBase_dn()
	{
		return base_dn;
	}

	public Hashtable<String, String> getAdmin_env()
	{
		return admin_env;
	}

}
