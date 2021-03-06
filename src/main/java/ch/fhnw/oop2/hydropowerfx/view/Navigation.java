package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.util.Duration;

/*
 * @author: Markus Winter
 */
public class Navigation extends BorderPane implements ViewMixin {
    private RootPM          rootPM;
    private StackPane       rootPanel;
    private TableView       powerStationTable;
    private HBox            navigation_Left,
                            navigation_Right;

    private ToggleButton    ButtonNav_ControlSave,
                            ButtonNav_ControlAdd,
                            ButtonNav_ControlRemove,
                            ButtonNav_ControlUndo,
                            ButtonNav_ControlRedo;

    private ToggleButton    ButtonNav_ViewText,
                            ButtonNav_ViewMap,
                            ButtonNav_ViewGrouped,
                            ButtonNav_ViewTime;

    private Label           Label_NavigationView;

    private FadeTransition  fadeTransitionOld,
                            fadeTransitionNew;

    private VBox            mainContentRight_Text,
                            mainContentRight_Map,
                            mainContentRight_Grouped,
                            mainContentRight_Time;

    private SplitPane       contentSplitPaneHorizontal;

    public Navigation(RootPM rootPM, StackPane rootPanel, TableView powerStationTable, VBox mainContentRight_Text, VBox mainContentRight_Map, VBox mainContentRight_Grouped, VBox mainContentRight_Time, SplitPane contentSplitPaneHorizontal) {
        this.rootPM                     = rootPM;
        this.rootPanel                  = rootPanel;
        this.powerStationTable          = powerStationTable;
        this.mainContentRight_Text      = mainContentRight_Text;
        this.mainContentRight_Map       = mainContentRight_Map;
        this.mainContentRight_Grouped   = mainContentRight_Grouped;
        this.mainContentRight_Time      = mainContentRight_Time;
        this.contentSplitPaneHorizontal = contentSplitPaneHorizontal;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("navigation");
        this.addStylesheetFiles("../css/Navigation.css");
    }

    @Override
    public void initializeControls() {
        navigation_Left           = new HBox();
        navigation_Right          = new HBox();

        ButtonNav_ControlSave     = new ToggleButton();
        ButtonNav_ControlAdd      = new ToggleButton();
        ButtonNav_ControlRemove   = new ToggleButton();
        ButtonNav_ControlUndo     = new ToggleButton();
        ButtonNav_ControlRedo     = new ToggleButton();

        ButtonNav_ViewText        = new ToggleButton();
        ButtonNav_ViewMap         = new ToggleButton();
        ButtonNav_ViewGrouped     = new ToggleButton();
        ButtonNav_ViewTime        = new ToggleButton();

        Label_NavigationView      = new Label();
    }

    @Override
    public void layoutControls() {
        ToggleGroup ViewButtonGroup = new ToggleGroup();
        ViewButtonGroup.setUserData(ButtonNav_ViewText);


        setButtonIcon(ButtonNav_ControlSave,   "control-save", "inactive");
        setButtonIcon(ButtonNav_ControlAdd,    "control-add", "");
        setButtonIcon(ButtonNav_ControlRemove, "control-remove", "inactive");
        setButtonIcon(ButtonNav_ControlUndo,   "control-undo", "inactive");
        setButtonIcon(ButtonNav_ControlRedo,   "control-redo", "inactive");


        setButtonIcon(ButtonNav_ViewText, "view-text", "");
        ButtonNav_ViewText.setUserData(mainContentRight_Text);
        ButtonNav_ViewText.setToggleGroup(ViewButtonGroup);
        ButtonNav_ViewText.setSelected(true);

        setButtonIcon(ButtonNav_ViewMap, "view-map", "");
        ButtonNav_ViewMap.setUserData(mainContentRight_Map);
        ButtonNav_ViewMap.setToggleGroup(ViewButtonGroup);

        setButtonIcon(ButtonNav_ViewGrouped, "view-grouped", "");
        ButtonNav_ViewGrouped.setUserData(mainContentRight_Grouped);
        ButtonNav_ViewGrouped.setToggleGroup(ViewButtonGroup);

        setButtonIcon(ButtonNav_ViewTime, "view-time", "");
        ButtonNav_ViewTime.setUserData(mainContentRight_Time);
        ButtonNav_ViewTime.setToggleGroup(ViewButtonGroup);

        ViewButtonGroup.selectedToggleProperty().addListener((ov, t, t1) -> {
            ToggleButton ButtonOld = (ToggleButton) ViewButtonGroup.getUserData();

            if(t1 != null && contentSplitPaneHorizontal.getItems().get(1).getId() != "spec-view-nothing-selected") {
                ToggleButton ButtonClicked = (ToggleButton) t1.getToggleGroup().getSelectedToggle();

                if (ButtonOld != ButtonClicked) {
                    if (animateChangeView((Pane) ButtonClicked.getUserData())) {
                        rootPM.setCurrentView(((Pane) ButtonClicked.getUserData()).getId());
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

        ButtonNav_ControlSave.setOnAction(event -> {
            if(!ButtonNav_ControlSave.getStyleClass().contains("inactive")) {
                rootPM.savePowerStationList();
            }
            ButtonNav_ControlSave.setSelected(false);
        });

        ButtonNav_ControlUndo.setOnAction(event -> {
            if(!ButtonNav_ControlUndo.getStyleClass().contains("inactive")) {
                rootPM.undoPowerStationList();
            }
            ButtonNav_ControlUndo.setSelected(false);
        });

        ButtonNav_ControlRedo.setOnAction(event -> {
            if(!ButtonNav_ControlRedo.getStyleClass().contains("inactive")) {
                rootPM.redoPowerStationList();
            }
            ButtonNav_ControlRedo.setSelected(false);
        });

        ButtonNav_ControlAdd.setOnAction(event -> {
            rootPM.addToPowerStationList();
            powerStationTable.getSelectionModel().selectLast();
            powerStationTable.scrollTo(powerStationTable.getSelectionModel().getFocusedIndex());
            ButtonNav_ControlAdd.setSelected(false);
        });

        ButtonNav_ControlRemove.setOnAction(event -> {
            if(!ButtonNav_ControlRemove.getStyleClass().contains("inactive")) {
                rootPM.removeFromPowerStationList(powerStationTable.getSelectionModel().getSelectedIndex());
            }
            ButtonNav_ControlRemove.setSelected(false);
        });

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

        this.setLeft(navigation_Left);
        this.setRight(navigation_Right);
    }

    @Override
    public void setupValueChangedListeners() {
        rootPM.buttonNavControlSaveActiveProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue) ButtonNav_ControlSave.getStyleClass().remove("inactive");
                    else ButtonNav_ControlSave.getStyleClass().add("inactive");
                }
        );

        rootPM.buttonNavControlUndoActiveProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue) ButtonNav_ControlUndo.getStyleClass().remove("inactive");
                    else ButtonNav_ControlUndo.getStyleClass().add("inactive");
                }
        );

        rootPM.buttonNavControlRedoActiveProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue) ButtonNav_ControlRedo.getStyleClass().remove("inactive");
                    else ButtonNav_ControlRedo.getStyleClass().add("inactive");
                }
        );

        rootPM.buttonNavControlRemoveActiveProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue) ButtonNav_ControlRemove.getStyleClass().remove("inactive");
                    else ButtonNav_ControlRemove.getStyleClass().add("inactive");
                }
        );

        this.rootPanel.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyCombSave = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombAdd = new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombRemove = new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombUndo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombRedo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombViewText = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombViewMap = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombViewGrouped = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN);
            final KeyCombination keyCombViewTime = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN);

            public void handle(KeyEvent ke) {
                if (keyCombSave.match(ke)) {
                    ButtonNav_ControlSave.fire();
                    ke.consume();
                }
                if (keyCombAdd.match(ke)) {
                    ButtonNav_ControlAdd.fire();
                    ke.consume();
                }
                if (keyCombRemove.match(ke)) {
                    ButtonNav_ControlRemove.fire();
                    ke.consume();
                }
                if (keyCombUndo.match(ke)) {
                    ButtonNav_ControlUndo.fire();
                    ke.consume();
                }
                if (keyCombRedo.match(ke)) {
                    ButtonNav_ControlRedo.fire();
                    ke.consume();
                }
                if (keyCombViewText.match(ke)) {
                    ButtonNav_ViewText.fire();
                    ke.consume();
                }
                if (keyCombViewMap.match(ke)) {
                    ButtonNav_ViewMap.fire();
                    ke.consume();
                }
                if (keyCombViewGrouped.match(ke)) {
                    ButtonNav_ViewGrouped.fire();
                    ke.consume();
                }
                if (keyCombViewTime.match(ke)) {
                    ButtonNav_ViewTime.fire();
                    ke.consume();
                }
            }
        });
    }

    @Override
    public void setupBindings() {

    }

    private boolean animateChangeView(Pane newView) {
        Node oldView = this.contentSplitPaneHorizontal.getItems().get(1);

        if((fadeTransitionOld == null || (fadeTransitionOld.getStatus() != Animation.Status.RUNNING && fadeTransitionNew.getStatus() != Animation.Status.RUNNING)) && oldView != newView) {
            Duration fadeDuration = Duration.millis(520);
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

    private void setButtonIcon(ToggleButton button, String icon, String additionalClass) {
        ImageView imageControlSave = new ImageView();
        imageControlSave.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/" + icon + ".png"));
        button.setGraphic(imageControlSave);
        button.getStyleClass().add(additionalClass);
    }


}
