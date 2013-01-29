package com.jared.core.common.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * see detail from web site: http://www.mybatis.org/core/zh/getting-started.html
 * @author junde.yang
 *
 */

public class SessionFactory {
	private static String resource = "mybatis.xml";
	private static SqlSessionFactory sqlSessionFactory = null;
	
	public static SqlSessionFactory getInstance(){
		if(sqlSessionFactory == null){
			try {
				Reader reader = Resources.getResourceAsReader(resource);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}
	
}
