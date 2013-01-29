package com.jared.core.dao;

import com.jared.core.model.People;

public interface PeopleDao  {
	int insert(People people);
	
	int update(People people);
	
	int delete(People people);

}
