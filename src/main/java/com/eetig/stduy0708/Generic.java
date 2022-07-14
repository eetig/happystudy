package com.eetig.stduy0708;

import com.eetig.vo.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @title: Generic
 * @author: xhong103
 * @date: 2022/7/8 15:05
 */

public class Generic {

    /**
     * 学习泛型方法:
     * 1.泛型方法有什么好处
     * 2.为什么需要使用泛型方法
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        //1.编译异报错: 关键在于方法中的c参数, Collection 不是 Collection 的子类型
        //          使用通配符 Collection<?> 也不行,因为Java 不允许把对象放进一个未知类型的集合里
        String[] strArr = {"a","b"};
        List<String> strList = new ArrayList<String>();
        //fromArrayToCollection(strArr,strList);



        //2.泛型方法


        //3.publi private
        Person per = new Person();
        String publicSex = per.getPublicSex();
        System.out.println("publicSex = " + publicSex);

        String name = per.name;
        int age = per.age;
        //没有per.sex


    }

    /**
     * @Description fromArrayToCollection 非泛型方法
     * @Author xhong103
     * @Date 2022-07-08 周五 下午 3:46
     * @param a
     * @param c
     * @return void
     */
    //static void fromArrayToCollection(Object[] a, Collection<Object> c){
    //    for (Object o : a) {
    //        c.add(o);
    //    }
    //}

    /**
     * @Description fromArrayToCollection 泛型方法
     * @Author xhong103
     * @Date 2022-07-08 周五 下午 3:19
     * @param a
     * @param c
     * @return void
     */
    static <T> void fromArrayToCollection(T[]a,Collection<T> c){
        for (T o : a) {
            c.add(o);
        }
    }


}
