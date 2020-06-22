package ewa.learnprogramming;


import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;


@RegisterMapper(TodoMapper.class)
public interface TodoDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO todo (title, content) VALUES (:title, :content)")
    Long createTodo(@BindBean Todo todo);

    @SqlUpdate("UPDATE todo SET title = :title, content = :content WHERE id = :id")
    Long updateTodo(@BindBean Todo todo);

    @SqlUpdate("DELETE FROM todo WHERE id = :id")
    Long deleteTodo( @BindBean Todo todo);

    @SqlQuery("SELECT id, title, content FROM todo")
    ImmutableList<Todo> getTodos();

    @SingleValueResult(Todo.class)
    @SqlQuery("SELECT id, title, content FROM todo WHERE id = :id")
    Optional<Todo> getTodo( @Bind("id") Long id);

}

