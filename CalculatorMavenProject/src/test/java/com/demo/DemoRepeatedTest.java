package com.demo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoRepeatedTest {


    @BeforeAll
    static  void setUp(){
        System.out.println("Executing @BeforeAll method");
    }


    @DisplayName("Test integer ")
    @RepeatedTest(value = 3,name="{displayName}. {currentRepetitions} ")
   // @ParameterizedTest
    //@MethodSource("integerSubstractionInputParameter")
    //@CsvSource({"64,4,16"})
    @CsvFileSource(resources = "/intergerDivision.csv")
    void testIntegerDivision_WhenDividendByDivisor_SholdReturnResult(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println("Repetition no "+repetitionInfo.getCurrentRepetition()+
                " of "+repetitionInfo.getTotalRepetitions());

        System.out.println("Running "+testInfo.getTestMethod().get().getName());

        Calculator calc=new Calculator();
        int result= calc.integerDivision(12,4);
        assertEquals(3,result,()-> 12+ " Integer division did not produce right result");
    }
}
