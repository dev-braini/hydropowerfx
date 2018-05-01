package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.model.PowerStation;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.util.Duration;

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
    private StackPane mainContentRight_Text;
    private StackPane mainContentRight_Map;
    private StackPane mainContentRight_Grouped;
    private StackPane mainContentRight_Time;
    private FadeTransition fadeTransitionOld;
    private FadeTransition fadeTransitionNew;

    private TableView<PowerStation> powerStationTable;
    private TableColumn<PowerStation, String> powerStationTable_Col0;
    private TableColumn<PowerStation, String> powerStationTable_Col1;
    private TableColumn<PowerStation, Double> powerStationTable_Col2;
    private TableColumn<PowerStation, Integer> powerStationTable_Col3;

    private Button ButtonNav_ViewText;
    private Button ButtonNav_ViewMap;
    private Button ButtonNav_ViewGrouped;
    private Button ButtonNav_ViewTime;

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
        mainContentRight_Text = new StackPane();
        mainContentRight_Map = new StackPane();
        mainContentRight_Grouped = new StackPane();
        mainContentRight_Time = new StackPane();

        powerStationTable = new TableView();
        powerStationTable_Col0 = new TableColumn<>();
        powerStationTable_Col1 = new TableColumn<>();
        powerStationTable_Col2 = new TableColumn<>();
        powerStationTable_Col3 = new TableColumn<>();

        ButtonNav_ViewText = new Button("TEXT");
        ButtonNav_ViewText.setOnAction(e -> {

        });
        ButtonNav_ViewMap = new Button("MAP");
        ButtonNav_ViewGrouped = new Button("GROUPED");
        ButtonNav_ViewTime = new Button("TIME");

        footer = new StackPane();
    }

    @Override
    public void layoutControls() {
        //navigation
        navigation.setId("navigation");
        navigation.setAlignment(Pos.CENTER);
        ButtonNav_ViewText.setOnAction(e -> {
            animateChangeView(mainContentRight_Text);
        });
        ButtonNav_ViewMap.setOnAction(e -> {
            animateChangeView(mainContentRight_Map);
        });
        ButtonNav_ViewGrouped.setOnAction(e -> {
            animateChangeView(mainContentRight_Grouped);
        });
        ButtonNav_ViewTime.setOnAction(e -> {
            animateChangeView(mainContentRight_Time);
        });
        navigation.getChildren().addAll(
                ButtonNav_ViewText,
                ButtonNav_ViewMap,
                ButtonNav_ViewGrouped,
                ButtonNav_ViewTime
        );

        //content wrapper
        contentSplitPaneVertical.setId("#content-split-pane-vertical");
        contentSplitPaneVertical.setOrientation(Orientation.VERTICAL);
        contentSplitPaneVertical.prefWidthProperty().bind(this.widthProperty());
        contentSplitPaneVertical.prefHeightProperty().bind(this.heightProperty());
        contentSplitPaneVertical.setDividerPositions(0.8f);
        contentSplitPaneVertical.getItems().addAll(contentSplitPaneHorizontal, footer);

        contentSplitPaneVertical.setId("#content-split-pane-horizontal");
        contentSplitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        contentSplitPaneHorizontal.setDividerPositions(0.32f);
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

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(powerStationTable);

        mainContentRight_Text.setId("main-content-right");
        mainContentRight_Text.getChildren().add(new Button("mainContentRight_Text"));

        mainContentRight_Map.setId("main-content-right");
        mainContentRight_Map.getChildren().add(new Button("mainContentRight_Map"));

        mainContentRight_Grouped.setId("main-content-right");
        mainContentRight_Grouped.getChildren().add(new Button("mainContentRight_Grouped"));

        mainContentRight_Time.setId("main-content-right");
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

    private void animateChangeView(StackPane newView) {
        if(fadeTransitionOld == null || fadeTransitionOld.getStatus() != Animation.Status.RUNNING) {
            Node oldView = contentSplitPaneHorizontal.getItems().get(1);
            newView.setOpacity(0);

            fadeTransitionOld = new FadeTransition(Duration.millis(777), oldView);
            fadeTransitionOld.setFromValue(1); fadeTransitionOld.setToValue(0);
            fadeTransitionOld.play();
            fadeTransitionOld.setOnFinished(event -> {
                contentSplitPaneHorizontal.getItems().remove(oldView);
                contentSplitPaneHorizontal.setDividerPositions(0.32f);
                contentSplitPaneHorizontal.getItems().add(newView);

                fadeTransitionNew = new FadeTransition(Duration.millis(777), newView);
                fadeTransitionNew.setFromValue(0); fadeTransitionNew.setToValue(1);
                fadeTransitionNew.play();
            });
        }
    }
}
