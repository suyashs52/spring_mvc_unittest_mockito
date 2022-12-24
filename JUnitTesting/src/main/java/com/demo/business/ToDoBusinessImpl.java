package com.demo.business;

import com.demo.data.api.ToDoService;

import java.util.ArrayList;
import java.util.List;

public class ToDoBusinessImpl
{
    private ToDoService toDoService;

    public ToDoBusinessImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public List<String > retrieveToDoRelatedToSpring(String user){
        List<String> filteredTodos = new ArrayList<String>();
        List<String> allTodos = toDoService.retrieveTodos(user);
        for (String todo : allTodos) {
            if (todo.contains("Spring")) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }

    public void deleteTodosNotRelatedToSpring(String user){

        List<String> allTodos = toDoService.retrieveTodos(user);
        for (String todo : allTodos) {
            if (todo.contains("Spring")==false) {
                 toDoService.deleteToDo(todo);
            }
        }

    }
}
