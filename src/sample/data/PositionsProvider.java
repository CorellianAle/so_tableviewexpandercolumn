package sample.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PositionsProvider
{
    public ObservableList<Position> get()
    {
        ObservableList<Position> positions = FXCollections.observableArrayList();

        positions.add(new Position(1, "Crook", "low level"));
        positions.add(new Position(2, "Dealer", "middle level"));
        positions.add(new Position(3, "Lord", "high level"));

        return positions;
    }
}
