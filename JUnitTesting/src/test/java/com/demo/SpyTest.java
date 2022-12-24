package com.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class SpyTest {
    //cannot mock final method, static method, equals, hashcode, private method

    @Test
    public void testMock(){
        List arrayList=mock(ArrayList.class);
        when(arrayList.size()).thenReturn(5);
        arrayList.add("Dummy");

        assertEquals(5,arrayList.size());
    }

    @Test
    public void testMockSpy(){
        List arrayList=spy(ArrayList.class); //create real object
        when(arrayList.size()).thenReturn(5); //all other method remain as it is, only size method override
        arrayList.add("Dummy");

        assertEquals(5,arrayList.size());
        verify(arrayList,never()).clear(); //clear method not called
    }
}
