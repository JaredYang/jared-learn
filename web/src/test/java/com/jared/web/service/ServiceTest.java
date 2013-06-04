package com.jared.web.service;

import com.jared.core.model.People;
import com.jared.core.service.Impl.PeopleServiceImpl;
import com.jared.core.service.PeopleService;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-1-29
 * Time: 下午3:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTest {
    @Test
    public void peopleServiceTest(){

        PeopleService peopleService = new PeopleServiceImpl();
        People people = new People();
        people.setName("杨俊德");
        people.setAge(25);
        people.setBirthDay(new Date());
        peopleService.insertPeople(people);
    }
}
