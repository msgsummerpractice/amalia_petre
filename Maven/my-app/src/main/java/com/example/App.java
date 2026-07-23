package com.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static int sum( int a, int b )
    {
        return a + b;
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println("Environment: " + System.getProperty("env"));
    }
}
