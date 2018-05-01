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
    private final RootPM rootPM;

    private VBox rootVBox;

    private HBox navigation;

    private SplitPane contentSplitPaneVertical;
    private SplitPane contentSplitPaneHorizontal;

    private StackPane mainContentLeft;
    private StackPane mainContentRight;

    private TableView<PowerStation> powerStationTable;
    private TableColumn<PowerStation, String> powerStationTable_Col0;
    private TableColumn<PowerStation, String> powerStationTable_Col1;
    private TableColumn<PowerStation, Double> powerStationTable_Col2;
    private TableColumn<PowerStation, Integer> powerStationTable_Col3;

    private StackPane footer;


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
        rootVBox = new VBox();

        navigation = new HBox();

        contentSplitPaneVertical = new SplitPane();
        contentSplitPaneHorizontal = new SplitPane();

        mainContentLeft = new StackPane();
        mainContentRight = new StackPane();

        powerStationTable = new TableView();
        powerStationTable_Col0 = new TableColumn<>();
        powerStationTable_Col1 = new TableColumn<>();
        powerStationTable_Col2 = new TableColumn<>();
        powerStationTable_Col3 = new TableColumn<>();

        footer = new StackPane();
    }

    @Override
    public void layoutControls() {
        //navigation
        navigation.setId("navigation");
        navigation.getChildren().add(new Button("#navigation"));

        //content wrapper
        contentSplitPaneVertical.setId("#content-split-pane-vertical");
        contentSplitPaneVertical.setOrientation(Orientation.VERTICAL);
        contentSplitPaneVertical.prefWidthProperty().bind(this.widthProperty());
        contentSplitPaneVertical.prefHeightProperty().bind(this.heightProperty());
        contentSplitPaneVertical.setDividerPositions(0.8f);
        contentSplitPaneVertical.getItems().addAll(contentSplitPaneHorizontal, footer);

        contentSplitPaneVertical.setId("#content-split-pane-horizontal");
        contentSplitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        contentSplitPaneHorizontal.setDividerPositions(0.3f);
        contentSplitPaneHorizontal.getItems().addAll(mainContentLeft, mainContentRight);

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

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(powerStationTable);

        mainContentRight.setId("main-content-right");
        mainContentRight.getChildren().add(new Button("#main-content-right"));

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
        //button.textProperty().bind(rootPM.greetingProperty());

        powerStationTable_Col0.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        powerStationTable_Col1.setCellValueFactory(new PropertyValueFactory<>("canton"));
        powerStationTable_Col1.setCellFactory(tc -> {
            TableCell<PowerStation, String> cell = new TableCell<PowerStation, String>() {
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


    }
}
