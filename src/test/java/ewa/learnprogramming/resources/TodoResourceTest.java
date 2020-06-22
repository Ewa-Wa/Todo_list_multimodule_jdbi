package ewa.learnprogramming.resources;

import com.google.common.collect.ImmutableList;
import ewa.learnprogramming.api.external.ExternalTodoLight;
import ewa.learnprogramming.api.external.ExternalTodoList;
import ewa.learnprogramming.api.request.TodoCreationRequest;
import ewa.learnprogramming.core.Todo;
import ewa.learnprogramming.jdbi.TodoDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class TodoResourceTest {

    //@Mock
    private UriInfo uriInfo;
    //@Mock
    private TodoDAO todoDAO;
    //@InjectMocks
    private TodoResource todoResource;

    @Before
    public void init(){
        this.uriInfo = mock(UriInfo.class);
        when(uriInfo.getBaseUri()).thenReturn(URI.create("baseURI"));
        this.todoDAO = mock(TodoDAO.class);
        this.todoResource = new TodoResource(this.todoDAO);
    }
    @After
    public void cleanUp() {
        this.uriInfo = null;
        this.todoDAO = null;
        this.todoResource = null;
    }

    @Test
    public void getTodos() {

        ImmutableList<Todo> todos = ImmutableList.of(Todo.Builder.aTodo(1, "title").build());
        ExternalTodoList expectedTodos = new ExternalTodoList.Mapper().fromTodoList(uriInfo.getBaseUri(), todos);
        when(this.todoDAO.getTodos()).thenReturn(todos);

        ExternalTodoList actualTodos = this.todoResource.getTodos(uriInfo);

        Assert.assertEquals("getting todos produces an ExternalTodoList with expected todos inside",
                expectedTodos.toString(), actualTodos.toString());


    }

    @Test
    public void createTodo() {
        TodoCreationRequest todoCreationRequest = new TodoCreationRequest("title", null);
        Todo todo = Todo.Builder.aTodo(0L, "title").build();
        ExternalTodoLight expectedTodo = new ExternalTodoLight.Mapper().fromId(uriInfo.getBaseUri(), 0L);
        when(this.todoDAO.createTodo(todo)).thenReturn(0L);

        ExternalTodoLight actualTodo = this.todoResource.createTodo(uriInfo, todoCreationRequest);

        Assert.assertThat("create a todo produces an ExternalTodoLight", actualTodo.toString(), is(expectedTodo.toString()));
    }

    @Test
    public void getTodo() {
    }

    @Test
    public void updateTodo() {
    }

    @Test
    public void deleteTodo() {
    }
}