package com.eetig.vo;

/**
 * @title: Person
 * @author: xhong103
 * @date: 2022/7/8 16:10
 */

public class Person {

    public int age;
    public String name;
    private String sex = "男";

    public String getName(){
        return "1";
    }
    private String getSex(){
        return "男";
    }
    public String getPublicSex(){
        return  sex;
    }

}
