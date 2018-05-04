package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/*
 * @author: Marco Peter & Markus Winter
 */
public class TextView extends StackPane implements ViewMixin {
    private final RootPM rootPM;
    private Button button;

    public TextView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("main-content-right-text");
        this.addStylesheetFiles("../css/TextView.css");
    }

    @Override
    public void initializeControls() {
        button                 = new Button();
    }

    @Override
    public void layoutControls() {
        button.setText("TEXT View");

        this.getChildren().add(button);
    }

    @Override
    public void setupBindings() {


    }
}
