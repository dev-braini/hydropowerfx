package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

/*
 * @author: Markus Winter
 */
public class PowerStationTable extends TableView implements ViewMixin {
    private final RootPM rootPM;

    private TableColumn<PowerStation, String> tableCol0;
    private TableColumn<PowerStation, String>  tableCol1;
    private TableColumn<PowerStation, Double>  tableCol2;
    private TableColumn<PowerStation, Integer> tableCol3;

    public PowerStationTable(RootPM rootPM) {
        this.rootPM = rootPM;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("power-station-table");
        this.addStylesheetFiles("../css/PowerStationTable.css");
    }

    @Override
    public void initializeControls() {
        tableCol0 = new TableColumn<>();
        tableCol1 = new TableColumn<>();
        tableCol2 = new TableColumn<>();
        tableCol3 = new TableColumn<>();
    }

    @Override
    public void layoutControls() {
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setEditable(true);

        tableCol0.setText("Name");            tableCol0.getStyleClass().add("col-name"); tableCol0.setMinWidth(140);
        tableCol1.setText("");                tableCol1.getStyleClass().add("col-canton"); tableCol1.setMinWidth(38); tableCol1.setMaxWidth(38); tableCol1.setResizable(false);

        tableCol2.setText("Leistung (MW)");   tableCol2.getStyleClass().add("col-performance");  tableCol2.setMinWidth(140); tableCol2.setMaxWidth(140); tableCol2.setResizable(false);
        tableCol3.setText("Inbetriebnahme");  tableCol3.getStyleClass().add("col-first-commissioning");  tableCol3.setMinWidth(140); tableCol3.setMaxWidth(140); tableCol3.setResizable(false);
        this.setId("power-station-table");
        this.getColumns().addAll(
                tableCol0,
                tableCol1,
                tableCol2,
                tableCol3
        );

        tableCol0.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCol0.setOnEditCommit(t -> rootPM.setName(t.getNewValue()));
        tableCol0.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        tableCol1.setCellValueFactory(new PropertyValueFactory<>("canton"));
        tableCol1.setCellFactory(tc -> {
            TableCell<PowerStation, String> cell = new TableCell<>() {
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String canton, boolean empty) {
                    super.updateItem(canton, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/images/canton/" + canton + ".png"));
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(24);
                        setGraphic(imageView);
                    }
                }
            };
            return cell;
        });
        tableCol2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableCol2.setOnEditCommit(t -> rootPM.setPerformance(t.getNewValue()));
        tableCol2.setCellValueFactory(cellData -> cellData.getValue().performanceProperty().asObject());
        tableCol3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableCol3.setOnEditCommit(t -> rootPM.setFirstCommissioning(t.getNewValue()));
        tableCol3.setCellValueFactory(cellData -> cellData.getValue().firstCommissioningProperty().asObject());

        this.setItems(this.rootPM.getPowerStationList());

        this.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        rootPM.showPowerStationDetails((PowerStation)newValue);
                        rootPM.setPowerStationTableSelectedIndex(this.getSelectionModel().getSelectedIndex());
                        rootPM.setButtonNavControlRemoveActive(true);
                    }
                });

        this.rootPM.powerStationTableSelectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.longValue() != -1) this.getSelectionModel().select(this.getItems().get((Integer) newValue));
                });

        this.getSortOrder().setAll(tableCol0);
    }

    @Override
    public void setupBindings() {

    }



}
