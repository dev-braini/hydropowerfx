package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.helper.PowerStationComboBox;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/*
 * @author: Marco Peter & Markus Winter
 */
public class TextView extends VBox implements ViewMixin {
    private final RootPM rootPM;


    private HBox                 infoHBox;

    private VBox                 info_LabelWrapper;
    private Label                infoLabel_Name,
                                 infoLabel_Location,
                                 infoLabel_Performance,
                                 infoLabel_FirstCommissioning;
    private Image                infoImage;
    private ImageView            infoImageView;

    private Pane                 spacer;

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
        infoHBox                     = new HBox();
        info_LabelWrapper            = new VBox();
        infoLabel_Name               = new Label("Laufenburg");
        infoLabel_Location           = new Label("Laufenburg AG");
        infoLabel_Performance        = new Label("106.0 MW");
        infoLabel_FirstCommissioning = new Label("1914");
        infoImage                    = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Wasserkraftwerk_Laufenburg1.jpg/1200px-Wasserkraftwerk_Laufenburg1.jpg", true);
        infoImageView                = new ImageView(infoImage);

        mainBox_ScrollWrapper        = new ScrollPane();
        mainBox                      = new GridPane();

        spacer                       = new Pane();

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
        info_LabelWrapper.setId("info-labels");
        infoLabel_Name.getStyleClass().add("info-label-title");
        info_LabelWrapper.getChildren().addAll(
                infoLabel_Name,
                infoLabel_Location,
                infoLabel_Performance,
                infoLabel_FirstCommissioning
        );

        infoImageView.setId("info-image");
        infoImageView.setPreserveRatio(true);
        infoImageView.setFitHeight(136);

        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        infoHBox.getStyleClass().add("default-padding");
        infoHBox.getChildren().addAll(info_LabelWrapper, spacer, infoImageView);

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

        mainBox.add(fieldUsedWaters,         0, 5, 3, 1);

        mainBox.add(fieldImageUrl,           0, 6, 3, 1);

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

    }
}
