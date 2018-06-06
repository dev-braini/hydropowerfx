package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.custom_controls.SwissLocation.SwissLocationControl;
import ch.fhnw.oop2.hydropowerfx.custom_controls.WaterQuantity.WaterQuantityControl;
import ch.fhnw.oop2.hydropowerfx.helper.PowerStationTextField;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;

import java.util.Locale;

/*
 * @author: Markus Winter
 */
public class SpecViewText extends VBox implements ViewMixin {
    private final RootPM          rootPM;

    private HBox                  infoHBox;

    private ScrollPane            mainBox_ScrollWrapper;
    private GridPane              mainBox;

    private TableView             powerStationTable;

    private PowerStationTextField fieldName,
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

    ////// Custom Controls //////
    private WaterQuantityControl  waterQuantityControl;
    private SwissLocationControl  swissLocationControl;


    public SpecViewText(RootPM model, TableView powerStationTable) {
        this.rootPM = model;
        this.powerStationTable = powerStationTable;
        this.setVisible(false);

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

        fieldName                    = new PowerStationTextField("Name*", powerStationTable, rootPM);
        fieldType                    = new PowerStationTextField("Typ", powerStationTable, rootPM);
        fieldLocation                = new PowerStationTextField("Standort", powerStationTable, rootPM);
        fieldCanton                  = new PowerStationTextField("Kanton", powerStationTable, rootPM);
        fieldWaterVolume             = new PowerStationTextField("Wassermenge (m³/s)", powerStationTable, rootPM, true);
        fieldPerformance             = new PowerStationTextField("Leistung (MW)", powerStationTable, rootPM, true);
        fieldFirstCommissioning      = new PowerStationTextField("Inbetriebnahme", powerStationTable, rootPM, true);
        fieldLastCommissioning       = new PowerStationTextField("Letzte", powerStationTable, rootPM, true);
        fieldDegreeOfLatitude        = new PowerStationTextField("Breitengrad", powerStationTable, rootPM, true);
        fieldDegreeOfLongitude       = new PowerStationTextField("Längengrad", powerStationTable, rootPM, true);
        fieldStatus                  = new PowerStationTextField("Status", powerStationTable, rootPM);
        fieldUsedWaters              = new PowerStationTextField("Genutzte Gewässer", powerStationTable, rootPM);
        fieldImageUrl                = new PowerStationTextField("Bild Speicherort", powerStationTable, rootPM);

        ////// Custom Controls //////
        waterQuantityControl = new WaterQuantityControl(0.0, rootPM.getWaterVolumeMax());
        swissLocationControl = new SwissLocationControl();
    }

    @Override
    public void layoutControls() {
        mainBox.setId("mainbox");
        mainBox.getStyleClass().add("default-padding");

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(65); col2.setMinWidth(65); col2.setHalignment(HPos.LEFT);

        ColumnConstraints col3 = new ColumnConstraints();
        mainBox.getColumnConstraints().addAll(col1,col2,col3);
        waterQuantityControl.setPadding(new Insets(8, 0, 200, 0));

        mainBox.add(fieldName,               0, 0);
        mainBox.add(fieldType,               2, 0);

        mainBox.add(fieldLocation,           0, 1);
        mainBox.add(fieldCanton,             2, 1);

        mainBox.add(fieldWaterVolume,        0, 2);
        mainBox.add(waterQuantityControl,    1, 2);
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
                mainBox_ScrollWrapper,
                swissLocationControl
        );
    }

    @Override
    public void setupBindings() {
        fieldName.getTextField().textProperty().bindBidirectional(               rootPM.nameProperty()                                            );
        fieldType.getTextField().textProperty().bindBidirectional(               rootPM.typeProperty()                                            );
        fieldLocation.getTextField().textProperty().bindBidirectional(           rootPM.locationProperty()                                        );
        fieldCanton.getTextField().textProperty().bindBidirectional(             rootPM.cantonProperty()                                          );
        fieldWaterVolume.getTextField().textProperty().bindBidirectional(        rootPM.waterVolumeProperty(), new NumberStringConverter("#######.##")        );
        fieldPerformance.getTextField().textProperty().bindBidirectional(        rootPM.performanceProperty(), new NumberStringConverter("#######.##")        );
        fieldFirstCommissioning.getTextField().textProperty().bindBidirectional( rootPM.firstCommissioningProperty(), new NumberStringConverter(Locale.GERMAN, "#####") );
        fieldLastCommissioning.getTextField().textProperty().bindBidirectional(  rootPM.lastCommissioningProperty(), new NumberStringConverter(Locale.GERMAN, "#####")  );
        fieldDegreeOfLatitude.getTextField().textProperty().bindBidirectional(   rootPM.degreeOfLatitudeProperty(), new NumberStringConverter()   );
        fieldDegreeOfLongitude.getTextField().textProperty().bindBidirectional(  rootPM.degreeOfLongitudeProperty(), new NumberStringConverter()  );
        fieldStatus.getTextField().textProperty().bindBidirectional(             rootPM.statusProperty()                                          );
        fieldUsedWaters.getTextField().textProperty().bindBidirectional(         rootPM.usedWatersProperty()                                      );
        fieldImageUrl.getTextField().textProperty().bindBidirectional(           rootPM.imageUrlProperty()                                        );

        fieldName.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()) fieldName.getTextField().requestFocus();
        });

        ////// Custom Controls //////
        waterQuantityControl.valueProperty().bind(rootPM.waterVolumeProperty());

        swissLocationControl.latitudeProperty().bindBidirectional(rootPM.degreeOfLatitudeProperty());
        swissLocationControl.longitudeProperty().bindBidirectional(rootPM.degreeOfLongitudeProperty());
        swissLocationControl.kantonProperty().bindBidirectional(rootPM.cantonProperty());
        /*swissLocationControl.latitudeFocusProperty().bind(rootPM.focusLatitudeProperty());
        swissLocationControl.longitudeFocusProperty().bind(rootPM.focusLongitudeProperty());*/

        /*rootPM.focusLatitudeProperty().bind(textFieldBreitengrad.focusedProperty());
        rootPM.focusLongitudeProperty().bind(textFieldLaengengrad.focusedProperty());*/
    }

}
