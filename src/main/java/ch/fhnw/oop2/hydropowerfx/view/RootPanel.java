package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.model.PowerStation;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;

/*
 * @author: Marco Peter & Markus Winter
 */
public class RootPanel extends StackPane implements ViewMixin {
    private final RootPM   rootPM;

    private VBox           rootVBox;

    private Navigation     navigation;

    private SplitPane      contentSplitPaneVertical,
                           contentSplitPaneHorizontal;

    private StackPane      mainContentLeft,
                           mainContentRight_Text,
                           mainContentRight_Map,
                           mainContentRight_Grouped,
                           mainContentRight_Time;

    private StackPane      footer;

    private TableView                          powerStationTable;
    private TableColumn<PowerStation, String>  powerStationTable_Col0;
    private TableColumn<PowerStation, String>  powerStationTable_Col1;
    private TableColumn<PowerStation, Double>  powerStationTable_Col2;
    private TableColumn<PowerStation, Integer> powerStationTable_Col3;


    public RootPanel(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        rootVBox                   = new VBox();

        contentSplitPaneVertical   = new SplitPane();
        contentSplitPaneHorizontal = new SplitPane();

        mainContentLeft            = new StackPane();
        mainContentRight_Text      = new StackPane();
        mainContentRight_Map       = new StackPane();
        mainContentRight_Grouped   = new StackPane();
        mainContentRight_Time      = new StackPane();

        powerStationTable          = new TableView();
        powerStationTable_Col0     = new TableColumn<>();
        powerStationTable_Col1     = new TableColumn<>();
        powerStationTable_Col2     = new TableColumn<>();
        powerStationTable_Col3     = new TableColumn<>();

        footer                     = new StackPane();

        navigation                 = new Navigation(mainContentRight_Text, mainContentRight_Map, mainContentRight_Grouped, mainContentRight_Time, contentSplitPaneHorizontal);
    }

    @Override
    public void layoutControls() {
        //content wrapper
        contentSplitPaneVertical.setId("#content-split-pane-vertical");
        contentSplitPaneVertical.setOrientation(Orientation.VERTICAL);
        contentSplitPaneVertical.prefWidthProperty().bind(this.widthProperty());
        contentSplitPaneVertical.prefHeightProperty().bind(this.heightProperty());
        contentSplitPaneVertical.setDividerPositions(0.8);
        contentSplitPaneVertical.getItems().addAll(contentSplitPaneHorizontal, footer);

        contentSplitPaneVertical.setId("#content-split-pane-horizontal");
        contentSplitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        contentSplitPaneHorizontal.setDividerPositions(0.32);
        contentSplitPaneHorizontal.getItems().addAll(mainContentLeft, mainContentRight_Text);

        //power station table
        powerStationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        powerStationTable_Col0.setText("Name");            powerStationTable_Col0.getStyleClass().add("col-name"); powerStationTable_Col0.setMinWidth(140);
        powerStationTable_Col1.setText("");                powerStationTable_Col1.getStyleClass().add("col-canton"); powerStationTable_Col1.setMinWidth(38); powerStationTable_Col1.setMaxWidth(38); powerStationTable_Col1.setResizable(false);

        powerStationTable_Col2.setText("Leistung (MW)");   powerStationTable_Col2.getStyleClass().add("col-performance");  powerStationTable_Col2.setMinWidth(140); powerStationTable_Col2.setMaxWidth(140); powerStationTable_Col2.setResizable(false);
        powerStationTable_Col3.setText("Inbetriebnahme");  powerStationTable_Col3.getStyleClass().add("col-first-commissioning");  powerStationTable_Col3.setMinWidth(140); powerStationTable_Col3.setMaxWidth(140); powerStationTable_Col3.setResizable(false);
        powerStationTable.setId("power-station-table");
        powerStationTable.getColumns().addAll(
                powerStationTable_Col0,
                powerStationTable_Col1,
                powerStationTable_Col2,
                powerStationTable_Col3
        );

        powerStationTable_Col0.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        powerStationTable_Col1.setCellValueFactory(new PropertyValueFactory<>("canton"));
        powerStationTable_Col1.setCellFactory(tc -> {
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
        powerStationTable_Col2.setCellValueFactory(cellData -> cellData.getValue().performanceProperty().asObject());
        powerStationTable_Col3.setCellValueFactory(cellData -> cellData.getValue().firstCommissioningProperty().asObject());

        powerStationTable.setItems(this.rootPM.getPowerStationList());

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(powerStationTable);

        mainContentRight_Text.setId("main-content-right-text");
        mainContentRight_Text.getChildren().add(new Button("mainContentRight_Text"));

        mainContentRight_Map.setId("main-content-right-map");
        mainContentRight_Map.getChildren().add(new Button("mainContentRight_Map"));

        mainContentRight_Grouped.setId("main-content-right-grouped");
        mainContentRight_Grouped.getChildren().add(new Button("mainContentRight_Grouped"));

        mainContentRight_Time.setId("main-content-right-time");
        mainContentRight_Time.getChildren().add(new Button("mainContentRight_Time"));

        //footer
        footer.setId("footer");
        footer.getChildren().add(new Button("footer"));

        //root wrapper
        rootVBox.setId("root-vbox");
        rootVBox.getChildren().add(0, navigation);
        rootVBox.getChildren().add(1, contentSplitPaneVertical);

        this.getChildren().add(rootVBox);
    }

    @Override
    public void setupBindings() {

    }
}
