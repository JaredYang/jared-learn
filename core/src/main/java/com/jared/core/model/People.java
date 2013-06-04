package com.jared.core.model;

import java.util.Date;

public class People {
    private Integer id;
    private String name;
    private Integer age;
    private Date birthDay;

    public People() {

    }

    public People(Integer id, String name, Integer age, Date birthDay) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthDay = birthDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

}
