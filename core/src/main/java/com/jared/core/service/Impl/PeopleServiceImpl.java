package com.jared.core.service.Impl;

import com.jared.core.dao.PeopleDao;
import com.jared.core.dao.impl.PeopleDaoImpl;
import com.jared.core.model.People;
import com.jared.core.service.PeopleService;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-29
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class PeopleServiceImpl implements PeopleService {
    @Override
    public void insertPeople(People people) {
        PeopleDao peopleDao = new PeopleDaoImpl();
        int res = peopleDao.insert(people);
    }
}
