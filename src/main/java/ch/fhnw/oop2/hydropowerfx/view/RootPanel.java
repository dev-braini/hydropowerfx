package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;

/*
 * @author: Marco Peter & Markus Winter
 */
public class RootPanel extends StackPane implements ViewMixin {
    private final RootPM   rootPM;

    private VBox           rootVBox;

    private Navigation     navigation;

    private SplitPane      contentSplitPaneVertical,
                           contentSplitPaneHorizontal;

    private StackPane      mainContentLeft;
    private TextView       mainContentRight_Text;
    private MapView        mainContentRight_Map;
    private GroupedView    mainContentRight_Grouped;
    private TimeView       mainContentRight_Time;

    private StackPane      footer;

    private TableView      powerStationTable;

    public RootPanel(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("../css/main.css");
    }

    @Override
    public void initializeControls() {
        rootVBox                   = new VBox();

        contentSplitPaneVertical   = new SplitPane();
        contentSplitPaneHorizontal = new SplitPane();

        mainContentLeft            = new StackPane();
        mainContentRight_Text      = new TextView(rootPM);
        mainContentRight_Map       = new MapView(rootPM);
        mainContentRight_Grouped   = new GroupedView(rootPM);
        mainContentRight_Time      = new TimeView(rootPM);

        powerStationTable          = new PowerStationTable(rootPM);

        footer                     = new StackPane();

        navigation                 = new Navigation(mainContentRight_Text, mainContentRight_Map, mainContentRight_Grouped, mainContentRight_Time, contentSplitPaneHorizontal);
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
        contentSplitPaneHorizontal.getItems().addAll(mainContentLeft, mainContentRight_Text);

        //main content
        mainContentLeft.setId("main-content-left");
        mainContentLeft.getChildren().add(powerStationTable);

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

    }
}
