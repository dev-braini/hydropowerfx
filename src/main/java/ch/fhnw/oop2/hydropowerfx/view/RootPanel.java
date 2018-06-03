package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.scene.text.Font;

import javax.print.attribute.standard.PDLOverrideSupported;

/*
 * @author: Markus Winter
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
    private StackPane       specViewNothingSelected;

    private VBox            welcomeBox;

    private Label           labelWelcomeTitle;
    private Label           labelWelcomeText;
    private Label           labelChoosePowerStation;

    private Image           imageLogo;
    private ImageView       imageViewLogo;

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
        specViewGrouped            = new SpecViewGrouped(rootPM, powerStationTable);
        specViewTime               = new SpecViewTime(rootPM, powerStationTable);
        specViewNothingSelected    = new StackPane();

        welcomeBox                 = new VBox();

        labelWelcomeTitle          = new Label();
        labelWelcomeText           = new Label();
        labelChoosePowerStation    = new Label();

        imageLogo                  = new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/logo.png");
        imageViewLogo              = new ImageView(imageLogo);

        footer                     = new Footer(rootPM, powerStationTable);

        navigation                 = new Navigation(rootPM, this,  powerStationTable, specViewText, specViewMap, specViewGrouped, specViewTime, contentSplitPaneHorizontal);
    }

    @Override
    public void layoutControls() {

        labelWelcomeTitle.setFont(new Font("Arial", 41.9));
        labelWelcomeTitle.setPadding(new Insets(0, 0, 13, 0));
        labelWelcomeTitle.setText("HydroPowerFX");

        labelWelcomeText.setFont(new Font("Arial", 16));
        labelWelcomeText.setPadding(new Insets(0, 0, 64, 0));
        labelWelcomeText.setText("OOP2 Projekt FS18 von Markus Winter");

        labelChoosePowerStation.setFont(new Font("Arial", 13));
        labelChoosePowerStation.setPadding(new Insets(144, 0, 0, 0));
        labelChoosePowerStation.setText("Bitte wählen Sie links ein Kraftwerk aus um zu starten.");

        welcomeBox.getChildren().addAll(labelWelcomeTitle, labelWelcomeText, imageViewLogo, labelChoosePowerStation);
        welcomeBox.setAlignment(Pos.CENTER);
        specViewNothingSelected.setId("spec-view-nothing-selected");
        specViewNothingSelected.getChildren().add(welcomeBox);

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
        contentSplitPaneHorizontal.getItems().addAll(mainContentLeft, specViewNothingSelected);

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
    public void setupValueChangedListeners() {
        powerStationTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(specViewNothingSelected.isVisible()) {
                        specViewText.setVisible(true);
                        specViewMap.setVisible(true);
                        specViewGrouped.setVisible(true);
                        specViewTime.setVisible(true);
                        specViewNothingSelected.setVisible(false);

                        Double dividerPosition = contentSplitPaneHorizontal.getDividerPositions()[0];
                        contentSplitPaneHorizontal.getItems().remove(specViewNothingSelected);
                        contentSplitPaneHorizontal.setDividerPositions(dividerPosition);
                        contentSplitPaneHorizontal.getItems().add(specViewText);
                    }
                });
    }

    @Override
    public void setupBindings() {

    }
}
