package com.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator Class")
class CalculatorTest {
    //right click on project>open module setting> select resource folder as test resource

    @BeforeAll
    static  void setUp(){
        System.out.println("Executing @BeforeAll method");
    }

    ///AAA Arrange Act Assert
   // @Test
    @DisplayName("Test integer ")
    @ParameterizedTest
    //@MethodSource("integerSubstractionInputParameter")
    //@CsvSource({"64,4,16"})
    @CsvFileSource(resources = "/intergerDivision.csv")
    void testIntegerDivision_WhenDividendByDivisor_SholdReturnResult(int dividend,int divisor,int expected) {
        Calculator calc=new Calculator();
       int result= calc.integerDivision(dividend,divisor);
       assertEquals(expected,result,()-> dividend+ " Integer division did not produce right result");
    }

    private static Stream<Arguments> integerSubstractionInputParameter(){
        return Stream.of(Arguments.of(64,4,16),
                Arguments.of(4,2,2)
        );
    }


    @ParameterizedTest
    @ValueSource(strings={"John","Kate","Alice"})
    void valueSourceDemonstration(String firstName){

        System.out.println(firstName);
        assertNotNull(firstName);

    }
}