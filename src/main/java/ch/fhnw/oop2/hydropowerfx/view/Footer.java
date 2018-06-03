package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.GroupedByCanton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableArray;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * @author: Marco Peter & Markus Winter
 */
public class Footer extends TableView implements ViewMixin {
    private final RootPM rootPM;
    private TableView    powerStationTable;

    private TableColumn<GroupedByCanton, String> tableCol0;
    private TableColumn<GroupedByCanton, String>  tableCol1;
    private TableColumn<GroupedByCanton, Double>  tableCol2;
    private TableColumn<GroupedByCanton, Integer> tableCol3;

    public Footer(RootPM rootPM, TableView powerStationTable) {
        this.rootPM = rootPM;
        this.powerStationTable = powerStationTable;

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
        tableCol0.setText("");            tableCol0.getStyleClass().add("col-name"); tableCol0.setMinWidth(38); tableCol0.setMaxWidth(38); tableCol0.setResizable(false);
        tableCol1.setText("Kanton");                tableCol1.getStyleClass().add("col-canton"); tableCol1.setMinWidth(140);

        tableCol2.setText("Leistung Total (MW)");   tableCol2.getStyleClass().addAll("col-performance", "align-center-right");  tableCol2.setMinWidth(148); tableCol2.setMaxWidth(148); tableCol2.setResizable(false);
        tableCol3.setText("Anzahl");  tableCol3.getStyleClass().addAll("col-first-commissioning", "align-center-right");  tableCol3.setMinWidth(90); tableCol3.setMaxWidth(90); tableCol3.setResizable(false);
        this.setId("footer-table");
        this.getStyleClass().addAll("groupedView", "withCanton");
        this.getColumns().addAll(
                tableCol0,
                tableCol1,
                tableCol2,
                tableCol3
        );

        tableCol0.setCellValueFactory(new PropertyValueFactory<>("cantonShortcut"));
        tableCol0.setCellFactory(tc -> {
            TableCell<GroupedByCanton, String> cell = new TableCell<>() {
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
        tableCol1.setCellValueFactory(cellData -> cellData.getValue().cantonNameProperty());
        //tableCol2.setCellValueFactory(cellData -> cellData.getValue().totalPerformanceProperty().asObject());

        tableCol2.setCellValueFactory(cellData -> {
            ObservableValue<Double> value = cellData.getValue().totalPerformanceProperty().asObject();
            double totalPerformance = Math.round(value.getValue()*100);
            cellData.getValue().setTotalPerformance(totalPerformance/100);

            return value;
        });

        tableCol3.setCellValueFactory(cellData -> cellData.getValue().numberOfPowerStationsProperty().asObject());

        this.setItems(this.rootPM.getPowerStationListByCanton());

        this.getSortOrder().setAll(tableCol1);
        this.getSelectionModel().selectFirst();
    }

    @Override
    public void setupValueChangedListeners() {
        powerStationTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                this.getSelectionModel().select(rootPM.getPowerStationIndexByCanton((PowerStation)newValue));
                this.scrollTo(rootPM.getPowerStationIndexByCanton((PowerStation)newValue));
            });
    }

    @Override
    public void setupBindings() {

    }
}
