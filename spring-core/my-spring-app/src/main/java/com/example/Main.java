package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "1" );
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println( "2" );
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld", HelloWorld.class);
        System.out.println( "3" );
        obj.getMessage();
    }
}
