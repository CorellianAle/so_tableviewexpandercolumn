package sample.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Number
{
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty number = new SimpleStringProperty();
    private IntegerProperty employee = new SimpleIntegerProperty();

    public Number()
    {
        id.set(0);
        number.set("");
        employee.set(0);
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

    public String getNumber()
    {
        return number.get();
    }

    public StringProperty numberProperty()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number.set(number);
    }

    public int getEmployee()
    {
        return employee.get();
    }

    public IntegerProperty employeeProperty()
    {
        return employee;
    }

    public void setEmployee(int employee)
    {
        this.employee.set(employee);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Number number1 = (Number) o;
        return Objects.equals(id, number1.id) &&
                Objects.equals(number, number1.number) &&
                Objects.equals(employee, number1.employee);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, number, employee);
    }
}
