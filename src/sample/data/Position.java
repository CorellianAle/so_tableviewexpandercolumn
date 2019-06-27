package sample.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Position
{
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();

    public Position()
    {
        id.set(0);
        name.set("");
        comment.set("");
    }

    public Position(int id, String name, String comment)
    {
        this.id.set(id);
        this.name.set(name);
        this.comment.set(comment);
    }

    public int getId()
    {
        return id.get();
    }

    public IntegerProperty idProperty()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id.set(id);
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getComment()
    {
        return comment.get();
    }

    public StringProperty commentProperty()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment.set(comment);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id) &&
                Objects.equals(name, position.name) &&
                Objects.equals(comment, position.comment);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, comment);
    }

    @Override
    public String toString()
    {
        if (name.get() == null)
        {
            return "position name not specified";
        }

        return name.get();
    }
}
