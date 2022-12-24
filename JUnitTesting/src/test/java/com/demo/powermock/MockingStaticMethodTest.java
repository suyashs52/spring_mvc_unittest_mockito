package com.demo.powermock;

import com.demo.Dependency;
import com.demo.SystemUnderTest;
import com.demo.UtilityClass;
import com.demo.business.ToDoBusinessImpl;
import com.demo.data.api.ToDoService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

 @RunWith(PowerMockRunner.class)
 @PrepareForTest(UtilityClass.class) //prepare the static class for mock
public class MockingStaticMethodTest {
     @Mock
     Dependency dependencyMock;

     @InjectMocks
     SystemUnderTest systemUnderTest;

     @Test
     public void powerMockito_MockingAStaticMethodCall() {

         when(dependencyMock.retrieveAllStats()).thenReturn(
                 Arrays.asList(1, 2, 3));

         PowerMockito.mockStatic(UtilityClass.class);

         when(UtilityClass.staticMethod(anyLong())).thenReturn(150);

         assertEquals(150, systemUnderTest.methodCallingAStaticMethod());

         //To verify a specific method call
         //First : Call PowerMockito.verifyStatic()
         //Second : Call the method to be verified
         PowerMockito.verifyStatic();
         UtilityClass.staticMethod(1 + 2 + 3);

         // verify exact number of calls
         //PowerMockito.verifyStatic(Mockito.times(1));

     }

}
