package com.demo.data.api;

import java.util.List;

public interface ToDoService {
    public List<String> retrieveTodos(String user);

    public void deleteToDo(String value);
}
