package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumbersProvider
{
    private final Object lock = new Object();
    private List<Number> numbers;
    private int nextId;

    public NumbersProvider()
    {
        numbers = new ArrayList<>();
    }

    public ObservableList<Number> get()
    {
        synchronized (lock)
        {
            ObservableList<Number> copies = FXCollections.observableArrayList();

            for (Number number : numbers)
            {
                Number copy = new Number();

                copy.setId(number.getId());
                copy.setNumber(new String(number.getNumber()));
                copy.setEmployee(number.getEmployee());

                copies.add(copy);
            }

            return copies;
        }
    }

    public void add(Number number)
    {
        synchronized (lock)
        {
            Number copy = new Number();

            copy.setId(nextId++);
            copy.setNumber(new String(number.getNumber()));
            copy.setEmployee(number.getEmployee());

            numbers.add(number);
        }
    }

    public void delete(int id)
    {
        synchronized (lock)
        {
            for (int i = 0; i < numbers.size(); ++i)
            {
                if (numbers.get(i).getId() == id)
                {
                    numbers.remove(i);
                    return;
                }
            }
        }
    }

    public void deleteByEmployee(int employeeId)
    {
        Iterator<Number> iterator = numbers.iterator();

        while (iterator.hasNext())
        {
            Number number = iterator.next();

            if (number.getEmployee() == employeeId)
            {
                iterator.remove();
            }
        }
    }

    public void update(Number number)
    {
        synchronized (lock)
        {
            for (int i = 0; i < numbers.size(); ++i)
            {
                if (numbers.get(i).getId() == number.getId())
                {
                    numbers.get(i).setNumber(new String(number.getNumber()));
                    numbers.get(i).setEmployee(number.getEmployee());
                }
            }
        }
    }
}
