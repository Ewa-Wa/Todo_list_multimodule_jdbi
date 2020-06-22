package ewa.learnprogramming.resources;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import ewa.learnprogramming.Todo;
import ewa.learnprogramming.TodoDAO;
import ewa.learnprogramming.external.ExternalTodo;
import ewa.learnprogramming.external.ExternalTodoLight;
import ewa.learnprogramming.external.ExternalTodoList;
import ewa.learnprogramming.request.TodoCreationRequest;
import ewa.learnprogramming.request.TodoUpdateRequest;
import io.dropwizard.jersey.params.LongParam;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TodoResourceTest {
    private UriInfo uriInfo= mock(UriInfo.class);
    private TodoDAO todoDAO=mock(TodoDAO.class);
    private TodoResource todoResource = new TodoResource(this.todoDAO);

    @BeforeEach
    public void setup(){
        when(uriInfo.getBaseUri()).thenReturn(URI.create("baseURI"));

    }
    @AfterEach
    public void cleanUp() {
        this.uriInfo = null;
        this.todoDAO = null;
        this.todoResource = null;
    }

    @Test
    public void getTodos() {
        ImmutableList<Todo> todos = ImmutableList.of(Todo.Builder.aTodo(0L, "title").build());
        ExternalTodoList expectedTodos = new ExternalTodoList.Mapper().
                fromTodoList(uriInfo.getBaseUri(), todos);
        when(this.todoDAO.getTodos()).thenReturn(todos);
        ExternalTodoList actualTodos = this.todoResource.getTodos(uriInfo);

        assertThat("getting todos produces an ExternalTodoList with expected todos inside",
                actualTodos, is(expectedTodos));
    }

    @Test
    void createTodo() {
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromId(uriInfo.getBaseUri(), 0L);
        when(this.todoDAO.createTodo(todo)).thenReturn(0L);

        ExternalTodoLight actualTodo = this.todoResource.createTodo(uriInfo, todoCreationRequest);

        assertThat("create a todo produces an ExternalTodoLight", actualTodo, is(expectedTodo));
    }

    @Test
    void getTodo() {
        long id = 0L;
        Todo todo = Todo.Builder.aTodo(id, "title").build();
        ExternalTodo expectedTodo = new ExternalTodo.Mapper().fromTodo(uriInfo.getBaseUri(), todo);
        when(this.todoDAO.getTodo(id)).thenReturn(Optional.of(todo));

        ExternalTodo actualTodo = this.todoResource.getTodo(uriInfo, new LongParam(Long.toString(id)));

        assertThat("get a todo with an existing id produces an ExternalTodo", actualTodo, is(expectedTodo));
    }


    @Test
    void updateTodo() {
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromId(uriInfo.getBaseUri(), 0L);
        when(this.todoDAO.updateTodo(todo)).thenReturn(1L);

        this.todoResource.updateTodo(uriInfo, new LongParam("0"), todoUpdateRequest);

        verify(this.todoDAO).updateTodo(todo);
    }

}