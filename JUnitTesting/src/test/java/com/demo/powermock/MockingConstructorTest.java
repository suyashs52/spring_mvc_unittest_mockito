package com.demo.powermock;

import com.demo.Dependency;
import com.demo.SystemUnderTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemUnderTest.class)
public class MockingConstructorTest {


    @InjectMocks
    SystemUnderTest systemUnderTest;

    @Mock
    ArrayList mockList;

    @Test
    public void powerMockito_MockingAStaticMethodCall() throws Exception {

        long result= Whitebox.invokeMethod(systemUnderTest,"privateMethodUnderTest");
        when(mockList.size()).thenReturn(10);
        //mock the array list constructor
        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(mockList);
        int size=systemUnderTest.methodUsingAnArrayListConstructor();

        assertEquals(10, size);



    }

}
