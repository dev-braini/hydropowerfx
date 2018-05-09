package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;

/*
 * @author: Marco Peter & Markus Winter
 */
public class RootPanel extends StackPane implements ViewMixin {
    private final RootPM    rootPM;

    private VBox            rootVBox;

    private Navigation      navigation;

    private SplitPane       contentSplitPaneVertical,
                            contentSplitPaneHorizontal;

    private StackPane       mainContentLeft;
    private TableView       powerStationTable;

    private SpecViewText    specViewText;
    private SpecViewMap     specViewMap;
    private SpecViewGrouped specViewGrouped;
    private SpecViewTime    specViewTime;

    private Footer          footer;

    public RootPanel(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("../css/Root.css");
    }

    @Override
    public void initializeControls() {
        rootVBox                   = new VBox();

        contentSplitPaneVertical   = new SplitPane();
        contentSplitPaneHorizontal = new SplitPane();

        powerStationTable          = new PowerStationTable(rootPM);

        mainContentLeft            = new StackPane();
        specViewText               = new SpecViewText(rootPM, powerStationTable);
        specViewMap                = new SpecViewMap(rootPM);
        specViewGrouped            = new SpecViewGrouped(rootPM);
        specViewTime               = new SpecViewTime(rootPM);

        footer                     = new Footer(rootPM);

        navigation                 = new Navigation(rootPM, powerStationTable, specViewText, specViewMap, specViewGrouped, specViewTime, contentSplitPaneHorizontal);
    }

    @Override
    public void layoutControls() {
        //content wrapper
        contentSplitPaneVertical.setId("#content-split-pane-vertical");
        contentSplitPaneVertical.setOrientation(Orientation.VERTICAL);
        contentSplitPaneVertical.prefWidthProperty().bind(this.widthProperty());
        contentSplitPaneVertical.prefHeightProperty().bind(this.heightProperty());
        contentSplitPaneVertical.setDividerPositions(0.78);
        contentSplitPaneVertical.getItems().addAll(contentSplitPaneHorizontal, footer);

        contentSplitPaneVertical.setId("#content-split-pane-horizontal");
        contentSplitPaneHorizontal.setOrientation(Orientation.HORIZONTAL);
        contentSplitPaneHorizontal.setDividerPositions(0.4);
        contentSplitPaneHorizontal.getItems().addAll(mainContentLeft, specViewText);

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(powerStationTable);

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
