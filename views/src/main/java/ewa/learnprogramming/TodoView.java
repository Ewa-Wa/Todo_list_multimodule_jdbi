package ewa.learnprogramming;

import io.dropwizard.views.View;

public class TodoView extends View {

    private final Todo todo;

    public TodoView(Todo todo) {
        super("todo.mustache");
        this.todo = todo;
    }

    public Todo getTodo() {
        return todo;
    }
}
