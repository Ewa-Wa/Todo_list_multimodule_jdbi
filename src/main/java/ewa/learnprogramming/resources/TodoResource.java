package ewa.learnprogramming.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import ewa.learnprogramming.api.external.ExternalTodo;
import ewa.learnprogramming.api.external.ExternalTodoLight;
import ewa.learnprogramming.api.external.ExternalTodoList;
import ewa.learnprogramming.api.request.TodoCreationRequest;
import ewa.learnprogramming.api.request.TodoUpdateRequest;
import ewa.learnprogramming.core.Todo;
import ewa.learnprogramming.jdbi.TodoDAO;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final TodoDAO todoDAO;

    public TodoResource(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }
    @Timed
    @GET
    public ExternalTodoList getTodos(@Context UriInfo uriInfo){
        return new ExternalTodoList.Mapper().fromTodoList(uriInfo.getBaseUri(),this.todoDAO.getTodos());
    }
    @Timed
    @POST
    public ExternalTodoLight createTodo(@Context UriInfo uriInfo,
                                        @Valid TodoCreationRequest todoCreationRequest) {
        Long createdTodoId = this.todoDAO.createTodo(new TodoCreationRequest.Mapper().toTodo(todoCreationRequest));
        return new ExternalTodoLight.Mapper().fromId(uriInfo.getBaseUri(),createdTodoId);
    }
    @Timed
    @Path("{id}")
    @GET
    public ExternalTodo getTodo(@Context UriInfo uriInfo, @PathParam("id") LongParam id) {
        Optional<Todo> todo = this.todoDAO.getTodo(id.get());
        if (todo.isPresent()) {
            return new ExternalTodo.Mapper().fromTodo(uriInfo.getBaseUri(),todo.get());
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    @Timed
    @Path("{id}")
    @PUT
    public void updateTodo(@Context UriInfo uriInfo, @PathParam("id") LongParam id, @Valid TodoUpdateRequest todoUpdateRequest) {
        Todo updatedTodo = new TodoUpdateRequest.Mapper().toTodo(id.get(), todoUpdateRequest);
        int nbRowUpdated = this.todoDAO.updateTodo(updatedTodo);
        if (nbRowUpdated == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    @Timed
    @Path("{id}")
    @DELETE
    public void deleteTodo(@Context UriInfo uriInfo, @PathParam("id") Long id ) {
        Todo deletedTodo = new Todo(id);
        this.todoDAO.deleteTodo(deletedTodo);
        }
}
