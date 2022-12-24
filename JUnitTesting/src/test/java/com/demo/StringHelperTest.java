package com.demo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StringHelperTest {

    @BeforeClass
   public static  void beforeClass(){
        System.out.println("Before class");
    }


    StringHelper helper;
    @Before
   public void before(){
        System.out.println("before test");
        helper=new StringHelper();

    }

    @Test
    public void truncateAInFirst2Positions() {

        assertEquals("CD", new StringHelper().truncateAInFirst2Positions("AACD"));
        assertEquals("CD", new StringHelper().truncateAInFirst2Positions("ACD"));
    }

    @Test
   public void areFirstAndLastTwoCharactersTheSame_BasicNegativeScenario() {
        boolean actualValue=helper.areFirstAndLastTwoCharactersTheSame("ABCD");
        boolean expected=false;
        assertFalse(actualValue);
    }
    @Test
    public void areFirstAndLastTwoCharactersTheSame_BasicPositiveScenario() {
        boolean actualValue=helper.areFirstAndLastTwoCharactersTheSame("ABAB");
        boolean expected=false;
        assertTrue(actualValue);
    }


    @Test
    public void testArray_RandomArray(){
        int[] numbers={12,3,4,1};
        int[] expected={1,3,4,12};

        Arrays.sort(numbers);

        assertArrayEquals(expected,numbers);
    }

    @Test(expected = NullPointerException.class)
   public void testArraySort_NullArray(){
        int[] num=null;


            Arrays.sort(num);


    }

    @Test(timeout = 1)
    public void testSort_Performance(){
        //assertTimeout(Duration.ofMillis(1),()->{
            int arrary []={12,23,4};

            for (int i=0;i<=1000000;i++){
                arrary[0]=i;
                Arrays.sort(arrary);
            }
        //});

    }
}