package ewa.learnprogramming.jdbi;


import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import ewa.learnprogramming.core.Todo;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

@RegisterMapper(TodoMapper.class)
public interface TodoDAO {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO todo (title, content) VALUES (:title, :content)")
    Long createTodo(@BindBean Todo todo);

    @SqlUpdate("UPDATE todo SET title = :title, content = :content WHERE id = :id")
    int updateTodo(@BindBean Todo todo);

    @SqlUpdate("DELETE FROM todo WHERE id = :id")
    int deleteTodo( @BindBean Todo todo);

    @SqlQuery("SELECT id, title, content FROM todo")
    ImmutableList<Todo> getTodos();

    @SingleValueResult(Todo.class)
    @SqlQuery("SELECT id, title, content FROM todo WHERE id = :id")
    Optional<Todo> getTodo( @Bind long id);

}

