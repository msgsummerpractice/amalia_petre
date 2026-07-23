package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        System.out.println("1");
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("2");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println("3");
        obj.getMessage();

        /* 
        @Bean
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage(); */
    }
}
