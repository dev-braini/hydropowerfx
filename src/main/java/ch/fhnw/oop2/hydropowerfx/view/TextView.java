package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.helper.PowerStationComboBox;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/*
 * @author: Marco Peter & Markus Winter
 */
public class TextView extends VBox implements ViewMixin {
    private final RootPM         rootPM;

    private HBox                 infoHBox;

    private ScrollPane           mainBox_ScrollWrapper;
    private GridPane             mainBox;

    private PowerStationComboBox fieldName,
                                 fieldType,
                                 fieldLocation,
                                 fieldCanton,
                                 fieldWaterVolume,
                                 fieldPerformance,
                                 fieldFirstCommissioning,
                                 fieldLastCommissioning,
                                 fieldDegreeOfLatitude,
                                 fieldDegreeOfLongitude,
                                 fieldStatus,
                                 fieldUsedWaters,
                                 fieldImageUrl;


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
        infoHBox                     = new PowerStationInfoBox(rootPM);

        mainBox_ScrollWrapper        = new ScrollPane();
        mainBox                      = new GridPane();

        fieldName                    = new PowerStationComboBox("Name*");
        fieldType                    = new PowerStationComboBox("Typ");
        fieldLocation                = new PowerStationComboBox("Standort");
        fieldCanton                  = new PowerStationComboBox("Kanton");
        fieldWaterVolume             = new PowerStationComboBox("Wassermenge (m³/s)");
        fieldPerformance             = new PowerStationComboBox("Leistung (MW)");
        fieldFirstCommissioning      = new PowerStationComboBox("Inbetriebnahme");
        fieldLastCommissioning       = new PowerStationComboBox("Letzte");
        fieldDegreeOfLatitude        = new PowerStationComboBox("Breitengrad");
        fieldDegreeOfLongitude       = new PowerStationComboBox("Längengrad");
        fieldStatus                  = new PowerStationComboBox("Status");
        fieldUsedWaters              = new PowerStationComboBox("Genutzte Gewässer");
        fieldImageUrl                = new PowerStationComboBox("Bild Speicherort");
    }

    @Override
    public void layoutControls() {
        mainBox.setId("mainbox");
        mainBox.getStyleClass().add("default-padding");

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(65); col2.setMinWidth(65);
        ColumnConstraints col3 = new ColumnConstraints();
        mainBox.getColumnConstraints().addAll(col1,col2,col3);


        mainBox.add(fieldName,               0, 0);
        mainBox.add(fieldType,               2, 0);

        mainBox.add(fieldLocation,           0, 1);
        mainBox.add(fieldCanton,             2, 1);

        mainBox.add(fieldWaterVolume,        0, 2);
        mainBox.add(fieldPerformance,        2, 2);

        mainBox.add(fieldFirstCommissioning, 0, 3);
        mainBox.add(fieldLastCommissioning,  2, 3);

        mainBox.add(fieldDegreeOfLatitude,   0, 4);
        mainBox.add(fieldDegreeOfLongitude,  2, 4);

        mainBox.add(fieldStatus,             0, 5);

        mainBox.add(fieldUsedWaters,         0, 6, 3, 1);

        mainBox.add(fieldImageUrl,           0, 7, 3, 1);

        mainBox_ScrollWrapper.getStyleClass().add("splitpane-no-border");
        mainBox_ScrollWrapper.setFitToWidth(true);
        mainBox_ScrollWrapper.setContent(mainBox);

        this.getChildren().addAll(
                infoHBox,
                mainBox_ScrollWrapper
        );
    }

    @Override
    public void setupBindings() {
        fieldName.getComboBox().valueProperty().bind(rootPM.nameProperty());
        fieldType.getComboBox().valueProperty().bind(rootPM.typeProperty());
        fieldLocation.getComboBox().valueProperty().bind(rootPM.locationProperty());
        fieldCanton.getComboBox().valueProperty().bind(rootPM.cantonProperty());
        fieldWaterVolume.getComboBox().valueProperty().bind(rootPM.waterVolumeProperty());
        fieldPerformance.getComboBox().valueProperty().bind(rootPM.performanceProperty());
        fieldFirstCommissioning.getComboBox().valueProperty().bind(rootPM.firstCommissioningProperty());
        fieldLastCommissioning.getComboBox().valueProperty().bind(rootPM.lastCommissioningProperty());
        fieldDegreeOfLatitude.getComboBox().valueProperty().bind(rootPM.degreeOfLatitudeProperty());
        fieldDegreeOfLongitude.getComboBox().valueProperty().bind(rootPM.degreeOfLongitudeProperty());
        fieldStatus.getComboBox().valueProperty().bind(rootPM.statusProperty());
        fieldUsedWaters.getComboBox().valueProperty().bind(rootPM.usedWatersProperty());
        fieldImageUrl.getComboBox().valueProperty().bind(rootPM.imageUrlProperty());
    }
}
