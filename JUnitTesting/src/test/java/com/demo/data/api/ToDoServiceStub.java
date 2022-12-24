package com.demo.data.api;

import com.demo.data.api.ToDoService;

import java.util.Arrays;
import java.util.List;

public class ToDoServiceStub implements ToDoService {
    @Override
    public List<String> retrieveTodos(String user) {
        return Arrays.asList("Learning Spring MVC","Learning Spring","Learn to test");
    }

    @Override
    public void deleteToDo(String value) {

    }
}
