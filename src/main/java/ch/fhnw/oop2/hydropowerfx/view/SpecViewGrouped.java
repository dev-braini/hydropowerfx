package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.swing.*;

/*
 * @author: Markus Winter
 */
public class SpecViewGrouped extends VBox implements ViewMixin {
    private final RootPM rootPM;
    private TableView    powerStationTable;
    private Label        labelCanton;
    private Label        labelWater;
    private Label        labelPerformance;

    private TableView    cantonTable;

    private TableView    waterTable;
    private TableColumn<GroupedByUsedWaters, String>  waterTable_Col0;
    private TableColumn<GroupedByUsedWaters, Double>  waterTable_Col1;
    private TableColumn<GroupedByUsedWaters, Integer> waterTable_Col2;

    private TableView    performanceTable;
    private TableColumn<GroupedByPerformance, String>   performanceTable_Col0;
    private TableColumn<GroupedByPerformance, Double>   performanceTable_Col1;
    private TableColumn<GroupedByPerformance, Integer>  performanceTable_Col2;

    public SpecViewGrouped(RootPM model, TableView powerStationTable) {
        this.rootPM = model;
        this.powerStationTable = powerStationTable;
        this.setVisible(false);

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("main-content-right-grouped");
        this.addStylesheetFiles("../css/GroupedView.css");
    }

    @Override
    public void initializeControls() {

        labelCanton            = new Label();
        labelWater             = new Label();
        labelPerformance       = new Label();

        cantonTable            = new Footer(rootPM, powerStationTable);

        waterTable             = new TableView();
        waterTable_Col0        = new TableColumn<>();
        waterTable_Col1        = new TableColumn<>();
        waterTable_Col2        = new TableColumn<>();

        performanceTable       = new TableView();
        performanceTable_Col0  = new TableColumn<>();
        performanceTable_Col1  = new TableColumn<>();
        performanceTable_Col2  = new TableColumn<>();
    }

    @Override
    public void layoutControls() {
        labelCanton.getStyleClass().add("table-label");
        labelCanton.setText("Nach Kantonen");
        labelWater.getStyleClass().add("table-label");
        labelWater.setText("Nach Gewässer (mind. 3 Kraftwerke)");
        labelPerformance.getStyleClass().add("table-label");
        labelPerformance.setText("Nach Leistung (MW)");

        waterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        waterTable.getStyleClass().add("groupedView");
        waterTable.setItems(rootPM.getPowerStationListByUsedWaters());
        waterTable_Col0.setText("Gewässer");
        waterTable_Col1.setText("Leistung Total (MW)"); waterTable_Col1.getStyleClass().add("align-center-right"); waterTable_Col1.setMinWidth(148); waterTable_Col1.setMaxWidth(148); waterTable_Col1.setResizable(false);
        waterTable_Col2.setText("Anzahl");              waterTable_Col2.getStyleClass().add("align-center-right"); waterTable_Col2.setMinWidth(90); waterTable_Col2.setMaxWidth(90); waterTable_Col2.setResizable(false);

        waterTable.getColumns().addAll(
                waterTable_Col0,
                waterTable_Col1,
                waterTable_Col2
        );

        waterTable_Col0.setCellValueFactory(cellData -> cellData.getValue().usedWatersProperty());
        //waterTable_Col1.setCellValueFactory(cellData -> cellData.getValue().totalPerformanceProperty().asObject());
        waterTable_Col1.setCellValueFactory(cellData -> {
            ObservableValue<Double> value = cellData.getValue().totalPerformanceProperty().asObject();
            double totalPerformance = Math.round(value.getValue()*100);
            cellData.getValue().setTotalPerformance(totalPerformance/100);

            return value;
        });
        waterTable_Col2.setCellValueFactory(cellData -> cellData.getValue().numberOfPowerStationsProperty().asObject());

        waterTable.getSortOrder().setAll(waterTable_Col0);
        waterTable_Col1.setSortType(TableColumn.SortType.DESCENDING);
        //waterTable.getSelectionModel().selectFirst();


        performanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        performanceTable.getStyleClass().add("groupedView");
        performanceTable.setItems(rootPM.getPowerStationListByPerformance());
        performanceTable_Col0.setText("Leistungsumfang (MW)");
        performanceTable_Col1.setText("Leistung Total (MW)");  performanceTable_Col1.getStyleClass().add("align-center-right"); performanceTable_Col1.setMinWidth(148); performanceTable_Col1.setMaxWidth(148); performanceTable_Col1.setResizable(false);
        performanceTable_Col2.setText("Anzahl");               performanceTable_Col2.getStyleClass().add("align-center-right"); performanceTable_Col2.setMinWidth(90); performanceTable_Col2.setMaxWidth(90); performanceTable_Col2.setResizable(false);

        performanceTable.getColumns().addAll(
                performanceTable_Col0,
                performanceTable_Col1,
                performanceTable_Col2
        );

        performanceTable_Col0.setCellValueFactory(cellData -> cellData.getValue().performanceRangeProperty());
        performanceTable_Col1.setCellValueFactory(cellData -> {
            ObservableValue<Double> value = cellData.getValue().totalPerformanceProperty().asObject();
            double totalPerformance = Math.round(value.getValue()*100);
            cellData.getValue().setTotalPerformance(totalPerformance/100);

            return value;
        });
        performanceTable_Col2.setCellValueFactory(cellData -> cellData.getValue().numberOfPowerStationsProperty().asObject());

        this.getChildren().addAll(
                labelCanton,
                cantonTable,
                labelWater,
                waterTable,
                labelPerformance,
                performanceTable
        );
    }

    @Override
    public void setupValueChangedListeners() {
        powerStationTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int cantonRow = rootPM.getPowerStationIndexByCanton((PowerStation)newValue);
                    int usedWatersRow = rootPM.getPowerStationIndexByUsedWaters((PowerStation)newValue);
                    int performanceRow = rootPM.getPowerStationIndexByPerformance((PowerStation)newValue);

                    cantonTable.getSelectionModel().select(cantonRow);
                    cantonTable.scrollTo(cantonRow);

                    if(usedWatersRow != -1) {
                        waterTable.getSelectionModel().select(usedWatersRow);
                        waterTable.scrollTo(usedWatersRow);
                    } else {
                        waterTable.getSelectionModel().clearSelection();
                    }

                    performanceTable.getSelectionModel().select(performanceRow);
                    performanceTable.scrollTo(performanceRow);
                });
    }

    @Override
    public void setupBindings() {


    }
}
