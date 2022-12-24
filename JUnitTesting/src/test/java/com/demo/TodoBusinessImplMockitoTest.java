package com.demo;

import com.demo.business.ToDoBusinessImpl;
import com.demo.data.api.ToDoService;
import com.demo.data.api.ToDoServiceStub;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class TodoBusinessImplMockitoTest {


    @Test
    public  void testRetrieveToDoRelatedToSpring_usingMock(){


        ToDoService toDoServiceMock=mock(ToDoService.class);
         List<String> todos=     Arrays.asList("Learning Spring MVC","Learning Spring","Learn to test");
        when(toDoServiceMock.retrieveTodos("dummy")).thenReturn(todos);
        ToDoBusinessImpl toDoBusinessImpl=new ToDoBusinessImpl(toDoServiceMock);

        List<String> filterToDos=toDoBusinessImpl.retrieveToDoRelatedToSpring("dummy");

        assertEquals(2,filterToDos.size());



    }

    @Test
    public  void testRetrieveToDoRelatedToSpring_usingMockWithEmptyList(){

    //given
        ToDoService toDoServiceMock=mock(ToDoService.class);
        List<String> todos=     Arrays.asList();
        when(toDoServiceMock.retrieveTodos("dummy")).thenReturn(todos);

        ToDoBusinessImpl toDoBusinessImpl=new ToDoBusinessImpl(toDoServiceMock);

        //when
        List<String> filterToDos=toDoBusinessImpl.retrieveToDoRelatedToSpring("dummy");

        //then
        assertEquals(1,filterToDos.size());


    }

    @Test
    public  void testRetrieveToDoRelatedToSpring_usingMockBDD(){

        //given
        ToDoService toDoServiceMock=mock(ToDoService.class);
        List<String> todos=     Arrays.asList();
        given(toDoServiceMock.retrieveTodos("dummy")).willReturn(todos);

        ToDoBusinessImpl toDoBusinessImpl=new ToDoBusinessImpl(toDoServiceMock);

        //when
        List<String> filterToDos=toDoBusinessImpl.retrieveToDoRelatedToSpring("dummy");

        //then
         assertThat (filterToDos.size(),is(0) ); //hamrest core is


    }


    @Test
    public  void testDeleteToDoNotRelatedToSpring_usingMockBDD_captureArgument(){

        //Declare Argument captor
        ArgumentCaptor<String> stringArgumentCaptor=ArgumentCaptor.forClass(String.class);

        //given
        ToDoService toDoServiceMock=mock(ToDoService.class);
        List<String> todos=     Arrays.asList("Learning Spring MVC","Learning Spring","Learn to test");
        given(toDoServiceMock.retrieveTodos("dummy")).willReturn(todos);

        ToDoBusinessImpl toDoBusinessImpl=new ToDoBusinessImpl(toDoServiceMock);

        //when
         toDoBusinessImpl.deleteTodosNotRelatedToSpring("dummy");

        //then
        verify(toDoServiceMock,times(1)).deleteToDo("Learn to test"); //this method call 1 times
         then(toDoServiceMock).should().deleteToDo("Learn to test");
        then(toDoServiceMock).should(never()).deleteToDo("Learn Spring");
        verify(toDoServiceMock,never()).deleteToDo("Learning Spring"); //verify that this is never called
        then(toDoServiceMock).should(never()).deleteToDo("Learning Sprinsgs");


        then(toDoServiceMock).should().deleteToDo(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(),is("Learn to test"));
        assertThat(stringArgumentCaptor.getAllValues().size(),is(1));


    }
}
