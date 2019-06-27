package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EmployeesProvider
{
    private final Object lock = new Object();
    private List<Employee> employees;
    private int nextId;

    public EmployeesProvider()
    {
        initialize();
    }

    public ObservableList<Employee> get()
    {
        synchronized (lock)
        {
            ObservableList<Employee> copies = FXCollections.observableArrayList();

            for (Employee employee : employees)
            {
                Employee copy = new Employee();

                copy.setId(employee.getId());
                copy.setName(new String(employee.getName()));
                copy.setComment(new String(employee.getComment()));
                copy.setPosition(employee.getPosition());

                copies.add(copy);
            }

            return copies;
        }
    }

    public void add(Employee employee)
    {
        synchronized (lock)
        {
            Employee copy = new Employee();

            copy.setId(nextId++);
            copy.setName(new String(employee.getName()));
            copy.setComment(new String(employee.getComment()));
            copy.setPosition(employee.getPosition());

            employees.add(employee);
        }
    }

    public void delete(int id)
    {
        synchronized (lock)
        {
            for (int i = 0; i < employees.size(); ++i)
            {
                if (employees.get(i).getId() == id)
                {
                    employees.remove(i);
                    return;
                }
            }
        }
    }

    public void update(Employee employee)
    {
        synchronized (lock)
        {
            for (int i = 0; i < employees.size(); ++i)
            {
                if (employees.get(i).getId() == employee.getId())
                {
                    employees.get(i).setName(new String(employee.getName()));
                    employees.get(i).setComment(new String(employee.getComment()));
                    employees.get(i).setPosition(employee.getPosition());
                }
            }
        }
    }

    private void initialize()
    {
        char nextId = 1;
        char nextName = 'A';
        char NextComment = 'a';

        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < 25; ++i)
        {
            Employee employee = new Employee();
            employee.setId(nextId++);
            employee.setName(Character.toString(nextName++));
            employee.setComment(Character.toString(NextComment++));
            employee.setPosition(1);

            employees.add(employee);
        }

        synchronized (lock)
        {
            this.employees = employees;
            this.nextId = nextId;
        }
    }
}
