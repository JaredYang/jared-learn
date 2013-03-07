package com.jared.core.dao.impl;

import com.jared.core.common.util.SessionFactory;
import com.jared.core.dao.PeopleDao;
import com.jared.core.model.People;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class PeopleDaoImpl implements PeopleDao {
	private SqlSessionFactory sessionFactory =  SessionFactory.getInstance();

	public int delete(People people) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(People people) {
		SqlSession session = null;
		int res = 0;
		try{
			session =  sessionFactory.openSession();
			res = session.insert("com.jared.core.dao.PeopleDao.insert", people);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return res;
	}

	public int update(People people) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
