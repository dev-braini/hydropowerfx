package ch.fhnw.oop2.hydropowerfx.helper;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PowerStationInfoBox extends HBox {
    private VBox                     info_LabelWrapper;
    private Label                    infoLabel_Name,
                                     infoLabel_Location,
                                     infoLabel_Performance,
                                     infoLabel_FirstCommissioning;
    private Image                    infoImage;
    private ImageView                infoImageView;
    private Pane                     spacer;

    public PowerStationInfoBox() {
        info_LabelWrapper            = new VBox();
        infoLabel_Name               = new Label("Laufenburg");
        infoLabel_Location           = new Label("Laufenburg AG");
        infoLabel_Performance        = new Label("106.0 MW");
        infoLabel_FirstCommissioning = new Label("1914");
        infoImage                    = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Wasserkraftwerk_Laufenburg1.jpg/1200px-Wasserkraftwerk_Laufenburg1.jpg", true);
        infoImageView                = new ImageView(infoImage);
        spacer                       = new Pane();

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

        this.getStyleClass().add("default-padding");
        this.getChildren().addAll(info_LabelWrapper, spacer, infoImageView);
    }

    @Override
    public Node getStyleableNode() {
        return this;
    }
}
