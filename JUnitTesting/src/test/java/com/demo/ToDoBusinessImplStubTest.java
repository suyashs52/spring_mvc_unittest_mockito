package com.demo;

import com.demo.business.ToDoBusinessImpl;
import com.demo.data.api.ToDoService;
import com.demo.data.api.ToDoServiceStub;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ToDoBusinessImplStubTest {

    @Test
    public  void testRetrieveToDoRelatedToSpring_usingAStub(){
        ToDoService toDoServiceStub=new ToDoServiceStub();
        ToDoBusinessImpl toDoBusinessImpl=new ToDoBusinessImpl(toDoServiceStub);
        List<String> filterToDos=toDoBusinessImpl.retrieveToDoRelatedToSpring("dummy");

        assertEquals(2,filterToDos.size());

        //stub class for different size -> have problem, maintaining is difficult when there is an
        // addition to method in interface


    }
}
