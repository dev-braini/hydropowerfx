package ch.fhnw.oop2.hydropowerfx.view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.util.Duration;

/*
 * @author: Marco Peter & Markus Winter
 */
public class Navigation extends BorderPane implements ViewMixin {
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

    private StackPane       mainContentRight_Text,
                            mainContentRight_Map,
                            mainContentRight_Grouped,
                            mainContentRight_Time;

    private SplitPane       contentSplitPaneHorizontal;

    public Navigation(StackPane mainContentRight_Text, StackPane mainContentRight_Map, StackPane mainContentRight_Grouped, StackPane mainContentRight_Time, SplitPane contentSplitPaneHorizontal) {
        this.mainContentRight_Text      = mainContentRight_Text;
        this.mainContentRight_Map       = mainContentRight_Map;
        this.mainContentRight_Grouped   = mainContentRight_Grouped;
        this.mainContentRight_Time      = mainContentRight_Time;
        this.contentSplitPaneHorizontal = contentSplitPaneHorizontal;

        init();
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
        //navigation
        this.setId("navigation");

        ToggleGroup ControlButtonGroup = new ToggleGroup();

        ToggleGroup ViewButtonGroup = new ToggleGroup();
        ViewButtonGroup.setUserData(ButtonNav_ViewText);


        setButtonIcon(ButtonNav_ControlSave,   "control-save"  );
        setButtonIcon(ButtonNav_ControlAdd,    "control-add"   );
        setButtonIcon(ButtonNav_ControlRemove, "control-remove");
        setButtonIcon(ButtonNav_ControlUndo,   "control-undo"  );
        setButtonIcon(ButtonNav_ControlRedo,   "control-redo"  );


        setButtonIcon(ButtonNav_ViewText, "view-text");
        ButtonNav_ViewText.setUserData(mainContentRight_Text);
        ButtonNav_ViewText.setToggleGroup(ViewButtonGroup);
        ButtonNav_ViewText.setSelected(true);

        setButtonIcon(ButtonNav_ViewMap, "view-map");
        ButtonNav_ViewMap.setUserData(mainContentRight_Map);
        ButtonNav_ViewMap.setToggleGroup(ViewButtonGroup);

        setButtonIcon(ButtonNav_ViewGrouped, "view-grouped");
        ButtonNav_ViewGrouped.setUserData(mainContentRight_Grouped);
        ButtonNav_ViewGrouped.setToggleGroup(ViewButtonGroup);

        setButtonIcon(ButtonNav_ViewTime, "view-time");
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

        this.setLeft(navigation_Left);
        this.setRight(navigation_Right);
    }

    @Override
    public void setupBindings() {


    }

    private boolean animateChangeView(StackPane newView) {
        Node oldView = this.contentSplitPaneHorizontal.getItems().get(1);

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

    private void setButtonIcon(ToggleButton button, String icon) {
        ImageView imageControlSave = new ImageView();
        imageControlSave.setImage(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/" + icon + ".png"));
        button.setGraphic(imageControlSave);
    }


}
