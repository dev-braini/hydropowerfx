package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
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

        footer = new StackPane();


    }

    @Override
    public void layoutControls() {
        //navigation
        navigation.setId("navigation");
        navigation.getChildren().add(new Button("#navigation"));

        //content wraüüer
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

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(new Button("#main-content-left"));

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
    }
}
