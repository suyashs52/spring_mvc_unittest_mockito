package com.demo;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListTest {

    @Test
    public  void testMockListSize(){
        List mockList =mock(List.class);
        when(mockList.size()).thenReturn(2);
        assertEquals(2,mockList.size());
    }

    @Test
    public  void testMockListSize_ReturnMultipleValue(){
        List mockList =mock(List.class);
        when(mockList.size()).thenReturn(2).thenReturn(3);
        assertEquals(2,mockList.size());
        assertEquals(3,mockList.size());
    }


    @Test
    public  void testMockListGet(){
        List mockList =mock(List.class);
        when(mockList.size()).thenReturn(2).thenReturn(3);
        when(mockList.get(anyInt())).thenReturn("inmins");  //argument matcher
        assertEquals("inmins",mockList.get(10));
    }


}
