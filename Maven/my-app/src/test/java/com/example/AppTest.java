package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public void testApp()
    {
        assertEquals( 5, App.sum(2, 3) );
    }

    public void testApp2()
    {
        assertTrue( 10 == App.sum(2, 8) );
    }

}
