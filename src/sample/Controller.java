package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.table.TableRowExpanderColumn;
import sample.data.*;
import sample.data.Number;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML GridPane gridPane;
    @FXML TableView tableView;

    @FXML Button button_updateEmployees;
    @FXML Button button_saveEmployee;
    @FXML Button button_saveAllEmployees;
    @FXML Button button_addEmployee;
    @FXML Button button_deleteEmployee;

    @FXML ProgressIndicator progressIndicator;

    ObjectProperty<EmployeeEx> selectedItem = new SimpleObjectProperty<>();

    private ListProperty<Position> positions = new SimpleListProperty<>();
    private ListProperty<EmployeeEx> employeeExs = new SimpleListProperty<>();

    private EmployeesProvider employeesProvider = new EmployeesProvider();
    private PositionsProvider positionsProvider = new PositionsProvider();
    private NumbersProvider numbersProvider = new NumbersProvider();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        initializeTableView();
        updateEmployees();
    }

    //region tableView

    private void initializeTableView()
    {
        if (tableView.itemsProperty().get() != null)
        {
            Bindings.unbindBidirectional(tableView.itemsProperty(), employeeExs);
        }

        //numbers ui
        TableRowExpanderColumn<EmployeeEx>expanderColumn = new TableRowExpanderColumn<EmployeeEx>(this::createEditor);
        tableView.getColumns().add(expanderColumn);

        //name
        TableColumn<EmployeeEx, String> tableColumn_firstName = new TableColumn<>("Name");
        tableColumn_firstName.setMinWidth(150);
        tableColumn_firstName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableColumn_firstName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumn_firstName.setOnEditCommit((TableColumn.CellEditEvent<EmployeeEx, String> event) ->
        {
            TablePosition<EmployeeEx, String> position = event.getTablePosition();
            String str = event.getNewValue();
            EmployeeEx employeeEx = event.getTableView().getItems().get(position.getRow());
            employeeEx.setName(str);
        });
        tableView.getColumns().add(tableColumn_firstName);

        //comment
        TableColumn<EmployeeEx, String> tableColumn_comment = new TableColumn<>("Comment");
        tableColumn_comment.setMinWidth(150);
        tableColumn_comment.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        tableColumn_comment.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumn_comment.setOnEditCommit((TableColumn.CellEditEvent<EmployeeEx, String> event) ->
        {
            TablePosition<EmployeeEx, String> position = event.getTablePosition();
            String str = event.getNewValue();
            EmployeeEx employeeEx = event.getTableView().getItems().get(position.getRow());
            employeeEx.setComment(str);
        });
        tableView.getColumns().add(tableColumn_comment);

        //position
        TableColumn<EmployeeEx, Position> tableColumn_position = new TableColumn<>("Position");
        tableColumn_position.setMinWidth(150);
        tableColumn_position.setCellValueFactory(param ->
        {
            EmployeeEx employeeEx = param.getValue();
            return employeeEx.positionProperty();
        });
        tableColumn_position.setCellFactory(ComboBoxTableCell.forTableColumn(positions));
        tableColumn_position.setOnEditCommit((TableColumn.CellEditEvent<EmployeeEx, Position> event) -> {
            TablePosition<EmployeeEx, Position> pos = event.getTablePosition();
            Position position = event.getNewValue();
            EmployeeEx employeeEx = event.getTableView().getItems().get(pos.getRow());
            employeeEx.setPosition(position);
        });

        tableView.getColumns().add(tableColumn_position);

        tableView.setItems(employeeExs);

        tableView.setOnMouseClicked((MouseEvent event) ->
        {
            //single click
            selectedRow();
        });

        tableView.setEditable(true);
    }

    private void selectedRow()
    {
        Object object = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem.get() != null)
        {
            //unbind
        }

        if (object == null)
        {
            return;
        }

        selectedItem.set((EmployeeEx) object);
    }

    //endregion

    //region expander column

    private Node createEditor(TableRowExpanderColumn.TableRowDataFeatures<EmployeeEx> param)
    {
        EmployeeEx employeeEx = param.getValue();

        param.expandedProperty().addListener((observable, oldValue, newValue) -> {
            int key = employeeEx.getId();
        });

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(5.0);

        Node nodeNumbers = createEmployeeNumbersNode(employeeEx);

        hbox.getChildren().add(nodeNumbers);

        return hbox;
    }

    private Node createEmployeeNumbersNode(EmployeeEx employeeEx)
    {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(5.0);

        Label label_numbers = new Label();
        label_numbers.setText("Numbers:");
        vbox.getChildren().add(label_numbers);
        VBox.setMargin(label_numbers, new Insets(0, 0, 10, 0));

        for (Number employeeEmail : employeeEx.getNumbers())
        {
            Node employeeEmailNode = createEmployeeEmailNode(employeeEmail);
            vbox.getChildren().add(employeeEmailNode);
        }

        Button buttonEmailAdd = new Button();
        buttonEmailAdd.setText("Add");
        buttonEmailAdd.setOnAction(event ->
        {
            addNumber(employeeEx);
        });

        vbox.getChildren().add(buttonEmailAdd);

        return vbox;
    }

    private Node createEmployeeEmailNode(Number number)
    {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5.0);

        Label label_number = new Label();
        label_number.setText("");
        hbox.getChildren().add(label_number);

        TextField textField_number = new TextField();
        textField_number.textProperty().bindBidirectional(number.numberProperty());
        hbox.getChildren().add(textField_number);

        Button button_deleteNumber = new Button();
        button_deleteNumber.setText("Delete");
        button_deleteNumber.setOnAction(event ->
        {
            deleteNumber(number);
        });
        hbox.getChildren().add(button_deleteNumber);

        return hbox;
    }

    //endregion


    //region data manipulation

    private void updateEmployees()
    {
        enableWait();

        Task<ObservableList<EmployeeEx>> taskUpdateEmployees = new Task<ObservableList<EmployeeEx>>()
        {
            @Override
            protected ObservableList<EmployeeEx> call() throws Exception
            {
                positions.set(positionsProvider.get());
                ObservableList<Employee> employees = employeesProvider.get();
                ObservableList<EmployeeEx> employeeExs = FXCollections.observableArrayList();

                ObservableList<Number> numbers = numbersProvider.get();

                for (Employee employee : employees)
                {
                    EmployeeEx employeeEx = EmployeeEx.fromEmployee(employee, positions, numbers);
                    employeeExs.add(employeeEx);
                }

                return employeeExs;
            }

            @Override
            protected void failed()
            {
                employeeExs.setValue(FXCollections.observableArrayList());
                Utility.showError(this.getException());

                disableWait();
            }

            @Override
            protected void succeeded()
            {
                employeeExs.setValue(getValue());
                disableWait();
            }
        };

        new Thread(taskUpdateEmployees).start();
    }

    private void addEmployee()
    {
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                employeesProvider.add(new Employee());

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());

                updateEmployees();
            }

            @Override
            protected void succeeded()
            {
                disableWait();

                updateEmployees();
            }
        };

        new Thread(task).start();
    }

    private void deleteEmployee()
    {
        //get selected
        Object object = tableView.getSelectionModel().getSelectedItem();

        if (object == null)
        {
            return;
        }

        EmployeeEx employeeEx = (EmployeeEx) object;

        //delete
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                numbersProvider.deleteByEmployee(employeeEx.getId());
                employeesProvider.delete(employeeEx.getId());

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());

                //update
                updateEmployees();
            }

            @Override
            protected void succeeded()
            {
                disableWait();

                //update
                updateEmployees();
            }
        };

        new Thread(task).start();
    }

    private void saveEmployee()
    {
        //get selected
        Object object = tableView.getSelectionModel().getSelectedItem();

        if (object == null)
        {
            return;
        }

        EmployeeEx employeeEx = (EmployeeEx) object;

        //save
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                Employee employee = EmployeeEx.toEmployee(employeeEx);
                employeesProvider.update(employee);

                for (Number number : employeeEx.getNumbers())
                {
                    numbersProvider.update(number);
                }

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());
            }

            @Override
            protected void succeeded()
            {
                disableWait();
            }
        };

        new Thread(task).start();
    }

    private void saveAllEmployees()
    {
        //save all
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                for (EmployeeEx employeeEx : employeeExs)
                {
                    Employee employee = EmployeeEx.toEmployee(employeeEx);
                    employeesProvider.update(employee);

                    for (Number number : employeeEx.getNumbers())
                    {
                        numbersProvider.update(number);
                    }
                }

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());
            }

            @Override
            protected void succeeded()
            {
                disableWait();
            }
        };

        new Thread(task).start();
    }

    private void addNumber(EmployeeEx employeeEx)
    {
        //add number
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                Number number = new Number();
                number.setEmployee(employeeEx.getId());

                numbersProvider.add(number);

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());

                //update
                updateEmployees();
            }

            @Override
            protected void succeeded()
            {
                disableWait();

                //update
                updateEmployees();
            }
        };

        new Thread(task).start();
    }

    private void deleteNumber(Number number)
    {
        enableWait();

        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                numbersProvider.delete(number.getId());

                return null;
            }

            @Override
            protected void failed()
            {
                disableWait();
                Utility.showError(this.getException());

                updateEmployees();
            }

            @Override
            protected void succeeded()
            {
                disableWait();

                updateEmployees();
            }
        };

        new Thread(task).start();
    }

    //endregion

    private void enableWait()
    {
        gridPane.setDisable(true);
        progressIndicator.setVisible(true);
    }

    private void disableWait()
    {
        gridPane.setDisable(false);
        progressIndicator.setVisible(false);
    }

    public void onAction_button_updateEmployees(ActionEvent actionEvent)
    {
        updateEmployees();
    }

    public void onAction_button_saveEmployee(ActionEvent actionEvent)
    {
        saveEmployee();
    }

    public void onAction_button_saveAllEmployees(ActionEvent actionEvent)
    {
        saveAllEmployees();
    }

    public void onAction_button_addEmployee(ActionEvent actionEvent)
    {
        addEmployee();
    }

    public void onAction_button_deleteEmployee(ActionEvent actionEvent)
    {
        deleteEmployee();
    }
}
