package ewa.learnprogramming.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import ewa.learnprogramming.Todo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class TodoUpdateRequest {

    @NotNull
    @Length(min = 1, max = 50)
    @JsonProperty
    private final String title;

    @Length(max = 500)
    @JsonProperty
    private final String content;

    @JsonCreator
    public TodoUpdateRequest(@JsonProperty("title") String title,
                             @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoUpdateRequest that = (TodoUpdateRequest) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return !(title != null ? !title.equals(that.title) : that.title != null);
    }

    public static class Mapper{

        public Todo toTodo(long id, TodoUpdateRequest todoUpdateRequest){
            return Todo.Builder.aTodo(id, todoUpdateRequest.getTitle()).
                    withContent(Optional.fromNullable(todoUpdateRequest.getContent())).build();

        }
    }
}
