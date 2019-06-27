package sample.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Employee
{
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private IntegerProperty position = new SimpleIntegerProperty();

    public Employee()
    {
        id.set(0);
        name.set("");
        comment.set("");
        position.set(0);
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

    public int getPosition()
    {
        return position.get();
    }

    public IntegerProperty positionProperty()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position.set(position);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(comment, employee.comment) &&
                Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, comment, position);
    }
}
