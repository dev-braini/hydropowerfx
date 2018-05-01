package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.model.PowerStation;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;

/*
 * @author: Marco Peter & Markus Winter
 */
public class RootPanel extends StackPane implements ViewMixin {
    private final RootPM   rootPM;

    private VBox           rootVBox;

    private BorderPane     navigation;
    private HBox           navigation_Left,
                           navigation_Right;

    private SplitPane      contentSplitPaneVertical,
                           contentSplitPaneHorizontal;

    private StackPane      mainContentLeft,
                           mainContentRight_Text,
                           mainContentRight_Map,
                           mainContentRight_Grouped,
                           mainContentRight_Time;

    private FadeTransition fadeTransitionOld,
                           fadeTransitionNew;

    private ToggleButton   ButtonNav_ControlSave,
                           ButtonNav_ControlAdd,
                           ButtonNav_ControlRemove,
                           ButtonNav_ControlUndo,
                           ButtonNav_ControlRedo;

    private ToggleButton   ButtonNav_ViewText,
                           ButtonNav_ViewMap,
                           ButtonNav_ViewGrouped,
                           ButtonNav_ViewTime;

    private Label          Label_NavigationView;

    private StackPane      footer;

    private TableView powerStationTable;
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

        navigation                 = new BorderPane();
        navigation_Left            = new HBox();
        navigation_Right           = new HBox();

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

        ButtonNav_ControlSave      = new ToggleButton();
        ButtonNav_ControlAdd       = new ToggleButton();
        ButtonNav_ControlRemove    = new ToggleButton();
        ButtonNav_ControlUndo      = new ToggleButton();
        ButtonNav_ControlRedo      = new ToggleButton();

        ButtonNav_ViewText         = new ToggleButton();
        ButtonNav_ViewMap          = new ToggleButton();
        ButtonNav_ViewGrouped      = new ToggleButton();
        ButtonNav_ViewTime         = new ToggleButton();

        Label_NavigationView      = new Label();

        footer                    = new StackPane();
    }

    @Override
    public void layoutControls() {
        //navigation
        navigation.setId("navigation");

        ToggleGroup ControlButtonGroup = new ToggleGroup();

        ToggleGroup ViewButtonGroup = new ToggleGroup();
        ViewButtonGroup.setUserData(ButtonNav_ViewText);

        ImageView imageControlSave = new ImageView();
        imageControlSave.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/control-save.png"));
        ButtonNav_ControlSave.setGraphic(imageControlSave);

        ImageView imageControlAdd = new ImageView();
        imageControlAdd.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/control-add.png"));
        ButtonNav_ControlAdd.setGraphic(imageControlAdd);

        ImageView imageControlRemove = new ImageView();
        imageControlRemove.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/control-remove.png"));
        ButtonNav_ControlRemove.setGraphic(imageControlRemove);

        ImageView imageControlUndo = new ImageView();
        imageControlUndo.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/control-undo.png"));
        ButtonNav_ControlUndo.setGraphic(imageControlUndo);

        ImageView imageControlRedo = new ImageView();
        imageControlRedo.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/control-redo.png"));
        ButtonNav_ControlRedo.setGraphic(imageControlRedo);


        ImageView imageViewText = new ImageView();
        imageViewText.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/view-text.png"));
        ButtonNav_ViewText.setGraphic(imageViewText);
        ButtonNav_ViewText.setUserData(mainContentRight_Text);
        ButtonNav_ViewText.setToggleGroup(ViewButtonGroup);
        ButtonNav_ViewText.setSelected(true);

        ImageView imageViewMap = new ImageView();
        imageViewMap.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/view-map.png"));
        ButtonNav_ViewMap.setGraphic(imageViewMap);
        ButtonNav_ViewMap.setUserData(mainContentRight_Map);
        ButtonNav_ViewMap.setToggleGroup(ViewButtonGroup);

        ImageView imageViewGrouped = new ImageView();
        imageViewGrouped.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/view-grouped.png"));
        ButtonNav_ViewGrouped.setGraphic(imageViewGrouped);
        ButtonNav_ViewGrouped.setUserData(mainContentRight_Grouped);
        ButtonNav_ViewGrouped.setToggleGroup(ViewButtonGroup);

        ImageView imageViewTime = new ImageView();
        imageViewTime.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/view-time.png"));
        ButtonNav_ViewTime.setGraphic(imageViewTime);

        ButtonNav_ViewTime.setUserData(mainContentRight_Time);
        ButtonNav_ViewTime.setToggleGroup(ViewButtonGroup);

        ViewButtonGroup.selectedToggleProperty().addListener((ov, t, t1) -> {
            ToggleButton ButtonOld = (ToggleButton) ViewButtonGroup.getUserData();

            if(t1 != null) {
                ToggleButton ButtonClicked = (ToggleButton) t1.getToggleGroup().getSelectedToggle();

                if (ButtonOld != ButtonClicked) {
                    if (animateChangeView((StackPane) ButtonClicked.getUserData())) {
                        ButtonClicked.setSelected(true);
                        ViewButtonGroup.setUserData(ButtonClicked);
                    } else {
                        ButtonClicked.setSelected(false);
                        ButtonOld.setSelected(true);
                    }
                }
            } else {
                ButtonOld.setSelected(true);
            }
        });

        Label_NavigationView.setText("Ansicht");

        navigation_Left.getChildren().addAll(
                ButtonNav_ControlSave,
                ButtonNav_ControlAdd,
                ButtonNav_ControlRemove,
                ButtonNav_ControlUndo,
                ButtonNav_ControlRedo,

                Label_NavigationView,
                ButtonNav_ViewText,
                ButtonNav_ViewMap,
                ButtonNav_ViewGrouped,
                ButtonNav_ViewTime
        );


        navigation_Right.getChildren().addAll(
        );

        navigation_Left.setId("navigation-left");
        navigation_Right.setId("navigation-right");
        navigation.setLeft(navigation_Left);
        navigation.setRight(navigation_Right);

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
        //button.textProperty().bind(rootPM.greetingProperty());

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
    }

    private boolean animateChangeView(StackPane newView) {
        Node oldView = contentSplitPaneHorizontal.getItems().get(1);

        if((fadeTransitionOld == null || (fadeTransitionOld.getStatus() != Animation.Status.RUNNING && fadeTransitionNew.getStatus() != Animation.Status.RUNNING)) && oldView != newView) {
            Duration fadeDuration = Duration.millis(700);
            newView.setOpacity(0);

            //fade out old view
            fadeTransitionOld = new FadeTransition(fadeDuration, oldView);
            fadeTransitionOld.setFromValue(1); fadeTransitionOld.setToValue(0);
            fadeTransitionOld.play();
            fadeTransitionOld.setOnFinished(event -> {
                //change views
                Double dividerPosition = contentSplitPaneHorizontal.getDividerPositions()[0];
                contentSplitPaneHorizontal.getItems().remove(oldView);
                contentSplitPaneHorizontal.setDividerPositions(dividerPosition);
                contentSplitPaneHorizontal.getItems().add(newView);

                //fade in new view
                fadeTransitionNew = new FadeTransition(fadeDuration, newView);
                fadeTransitionNew.setFromValue(0); fadeTransitionNew.setToValue(1);
                fadeTransitionNew.play();

            });

            return true;
        }

        return false;
    }
}
