package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/*
 * @author: Marco Peter & Markus Winter
 */
public class SpecViewGrouped extends VBox implements ViewMixin {
    private final RootPM rootPM;
    private Label       labelCanton;
    private Label       labelWater;
    private Label       labelPerformance;

    private TableView   cantonTable;
    private TableColumn cantonTable_Col0;
    private TableColumn cantonTable_Col1;
    private TableColumn cantonTable_Col2;
    private TableColumn cantonTable_Col3;

    private TableView   waterTable;
    private TableColumn waterTable_Col0;
    private TableColumn waterTable_Col1;
    private TableColumn waterTable_Col2;

    private TableView   performanceTable;
    private TableColumn performanceTable_Col0;
    private TableColumn performanceTable_Col1;
    private TableColumn performanceTable_Col2;

    public SpecViewGrouped(RootPM model) {
        this.rootPM = model;

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

        cantonTable            = new TableView();
        cantonTable_Col0       = new TableColumn<>();
        cantonTable_Col1       = new TableColumn<>();
        cantonTable_Col2       = new TableColumn<>();
        cantonTable_Col3       = new TableColumn<>();

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
        labelWater.setText("Nach Gewässer");
        labelPerformance.getStyleClass().add("table-label");
        labelPerformance.setText("Nach Leistung (MW)");

        cantonTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cantonTable_Col0.setText("    ");
        cantonTable_Col1.setText("Kanton");
        cantonTable_Col2.setText("Leistung Total (MW)");
        cantonTable_Col3.setText("Anzahl");

        waterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        waterTable_Col0.setText("Gewässer");
        waterTable_Col1.setText("Leistung Total (MW)");
        waterTable_Col2.setText("Anzahl");

        performanceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        performanceTable_Col0.setText("Leistungsumfang (MW)");
        performanceTable_Col1.setText("Leistung Total (MW)");
        performanceTable_Col2.setText("Anzahl");

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
    public void setupBindings() {


    }
}
