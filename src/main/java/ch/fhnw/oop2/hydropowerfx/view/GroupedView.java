package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/*
 * @author: Marco Peter & Markus Winter
 */
public class GroupedView extends StackPane implements ViewMixin {
    private final RootPM rootPM;
    private Button button;

    public GroupedView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("main-content-right-grouped");
    }

    @Override
    public void initializeControls() {
        button                 = new Button();
    }

    @Override
    public void layoutControls() {
        button.setText("GROUPED View");

        this.getChildren().add(button);
    }

    @Override
    public void setupBindings() {


    }
}
