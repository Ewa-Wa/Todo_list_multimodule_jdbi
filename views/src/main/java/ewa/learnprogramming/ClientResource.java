package ewa.learnprogramming;

//import com.sun.jersey.api.client.Client;

import com.google.common.base.Optional;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.TEXT_HTML)
@Path("/client")
public class ClientResource {

    private Client client;
    private TodoDAO todoDAO;

    public ClientResource(Client client, TodoDAO todoDAO) {
        this.client = client;
        this.todoDAO = todoDAO;
    }

    @GET
    @Path("/showTodo/{id}")
    public TodoView showTodo(@PathParam("id") LongParam id) {
        Optional<Todo> todo = this.todoDAO.getTodo(id.get());
        return new TodoView(todo.get());
    }
    @GET
    @Path("/showTodos")
    public TodoView showTodos(@PathParam("id") LongParam id) {
        Optional<Todo> todo = this.todoDAO.getTodo(id.get());
        return new TodoView(todo.get());
    }
}
