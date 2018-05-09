package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.layout.*;

/*
 * @author: Marco Peter & Markus Winter
 */
public class Footer extends StackPane implements ViewMixin {
    private RootPM          rootPM;

    public Footer(RootPM rootPM) {
        this.rootPM                     = rootPM;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("navigation");
        this.addStylesheetFiles("../css/Navigation.css");
    }

    @Override
    public void initializeControls() {

    }

    @Override
    public void layoutControls() {

    }

    @Override
    public void setupBindings() {

    }
}
