package com.demo;

import org.junit.jupiter.api.*;

//@TestMethodOrder(MethodOrderer.Random.class)
//@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_METHOD) //single each method has 1 instance
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //single each method has only 1 instance for all method
//
@Order(1)
public class MethodRandomeOrderTest {

    @AfterEach
    void afterEach(){
        System.out.println("The state of instance object is "+compe);
    }

    @AfterAll //need not be static as using lifecycle per class
    void afterAll(){

    }
    StringBuilder compe=new StringBuilder();
    @Test
    @Order(1)
    void testA(){
        compe.append("1");
        System.out.println("Running test A");
    }
    @Test
    void testB(){
        compe.append("2");
        System.out.println("Running test B");
    }
    @Test
    @Order(2)
    void testC(){
        compe.append("3");
        System.out.println("Running test C");
    }
    @Test
    void testD(){
        compe.append("4");
        System.out.println("Running test D");
    }
}
