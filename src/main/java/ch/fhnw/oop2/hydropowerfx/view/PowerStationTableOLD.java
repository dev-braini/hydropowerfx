package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.model.PowerStation;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerStationTableOLD extends TableView {
    private TableColumn<PowerStation, String>  table_Col0;
    private TableColumn<PowerStation, String>  table_Col1;
    private TableColumn<PowerStation, Double>  table_Col2;
    private TableColumn<PowerStation, Integer> table_Col3;

    public void Navigation() {
        table_Col0     = new TableColumn<>();
        table_Col1     = new TableColumn<>();
        table_Col2     = new TableColumn<>();
        table_Col3     = new TableColumn<>();
    }


}
