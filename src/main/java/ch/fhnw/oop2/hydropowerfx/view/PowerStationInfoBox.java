package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.custom_controls.SwissLocation.SwissLocationControl;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PowerStationInfoBox extends HBox implements ViewMixin {
    private final RootPM               rootPM;
    private final SwissLocationControl swissLocationControl;
    private VBox                       info_LabelWrapper;
    private HBox                       infoLabel_LocationCanton;
    private Label                      infoLabel_Name,
                                       infoLabel_Location,
                                       infoLabel_Canton,
                                       infoLabel_Performance,
                                       infoLabel_FirstCommissioning;
    private Image                      infoImage;
    private ImageView                  infoImageView;
    private Pane                       spacer,
                                       spacer2;

    public PowerStationInfoBox(RootPM rootPM, SwissLocationControl swissLocationControl) {
        this.rootPM               = rootPM;
        this.swissLocationControl = swissLocationControl;

        init();
    }

    @Override
    public Node getStyleableNode() {
        return this;
    }

    @Override
    public void initializeControls() {
        info_LabelWrapper            = new VBox();
        infoLabel_Name               = new Label("Laufenburg");
        infoLabel_Location           = new Label("Laufenburg");
        infoLabel_Canton             = new Label("AG");
        infoLabel_LocationCanton     = new HBox();
        infoLabel_Performance        = new Label("106.0 MW");
        infoLabel_FirstCommissioning = new Label("1914");
        infoImage                    = new Image("https://upload.wikimedia.org/wikipedia/commons/c/ce/Transparent.gif", true);
        infoImageView                = new ImageView(infoImage);
        spacer                       = new Pane();
        spacer2                      = new Pane();
    }

    @Override
    public void layoutControls() {
        infoLabel_LocationCanton.getChildren().addAll(infoLabel_Location, infoLabel_Canton);
        infoLabel_LocationCanton.getStyleClass().add("info-label-location-canton");
        //infoLabel_LocationCanton.setSpacing(7);
        info_LabelWrapper.setId("info-labels");
        infoLabel_Name.getStyleClass().add("info-label-title");
        info_LabelWrapper.getChildren().addAll(
                infoLabel_Name,
                infoLabel_LocationCanton,
                infoLabel_Performance,
                infoLabel_FirstCommissioning
        );

        infoImageView.setId("info-image");
        infoImageView.setPreserveRatio(true);
        infoImageView.setFitHeight(136);
        if(swissLocationControl != null) infoImageView.setFitWidth(140);

        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        spacer2.setMinSize(10, 1);

        this.getStyleClass().add("default-padding");

        this.getChildren().addAll(info_LabelWrapper, spacer, infoImageView);

        if(swissLocationControl != null) this.getChildren().addAll(spacer2, swissLocationControl);
    }

    @Override
    public void setupBindings() {
        infoLabel_Name.textProperty().bind(rootPM.nameProperty());
        infoLabel_Location.textProperty().bind(rootPM.locationProperty());
        infoLabel_Canton.textProperty().bind(rootPM.cantonProperty());
        infoLabel_Performance.textProperty().bind(Bindings.convert(rootPM.performanceProperty()));
        infoLabel_FirstCommissioning.textProperty().bind(Bindings.convert(rootPM.firstCommissioningProperty()));

        rootPM.imageUrlProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == null || newValue.isEmpty()) newValue = "https://upload.wikimedia.org/wikipedia/commons/c/ce/Transparent.gif";

            infoImage = new Image(newValue, true);
            infoImageView.setImage(infoImage);
        }));

        //infoImageView
        /*if(rootPM.imageUrlProperty().toString() != "") {
            System.out.println(rootPM.imageUrlProperty());
            infoImage = new Image(rootPM.imageUrlProperty().toString(), true);
            infoImageView.setImage(infoImage);
        }*/
    }
}
