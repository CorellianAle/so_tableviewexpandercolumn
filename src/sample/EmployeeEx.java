package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.data.Employee;
import sample.data.Number;
import sample.data.Position;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmployeeEx
{
    IntegerProperty id = new SimpleIntegerProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty comment = new SimpleStringProperty();
    ObjectProperty<Position> position = new SimpleObjectProperty<>();
    ListProperty<Number> numbers = new SimpleListProperty<>();

    public EmployeeEx()
    {
        id.set(0);
        name.set("");
        comment.set("");
        position.set(null);
        numbers.set(FXCollections.observableArrayList());
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

    public Position getPosition()
    {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position.set(position);
    }

    public ObservableList<Number> getNumbers()
    {
        return numbers.get();
    }

    public ListProperty<Number> numbersProperty()
    {
        return numbers;
    }

    public void setNumbers(ObservableList<Number> numbers)
    {
        this.numbers.set(numbers);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEx that = (EmployeeEx) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(position, that.position) &&
                Objects.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, comment, position, numbers);
    }

    public static Employee toEmployee(EmployeeEx employeeEx)
    {
        Employee employee = new Employee();

        employee.setId(employeeEx.getId());
        employee.setName(employeeEx.getName());
        employee.setComment(employeeEx.getComment());

        if (employeeEx.getPosition() != null)
        {
            employee.setPosition(employeeEx.getPosition().getId());
        }

        return employee;
    }

    public static EmployeeEx fromEmployee(Employee employee, List<Position> positions, List<Number> numbers)
    {
        EmployeeEx employeeEx = new EmployeeEx();

        employeeEx.setId(employee.getId());
        employeeEx.setName(employee.getName());
        employeeEx.setComment(employee.getComment());

        //
        Optional<Position> position = positions.stream().filter(c -> c.getId() == employee.getPosition()).findFirst();

        if (position.isPresent())
        {
            employeeEx.setPosition(position.get());
        }

        //
        numbers.stream().filter(n -> n.getEmployee() == employee.getId()).forEach(number -> {
            employeeEx.getNumbers().add(number);
        });

        return employeeEx;
    }
}
