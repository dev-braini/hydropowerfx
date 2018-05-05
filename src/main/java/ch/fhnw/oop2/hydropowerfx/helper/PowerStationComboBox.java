package ch.fhnw.oop2.hydropowerfx.helper;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PowerStationComboBox extends HBox {

    public PowerStationComboBox(String labelName) {
        ComboBox comboBox = new ComboBox();

        comboBox.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );

        comboBox.setValue("Normal");
        comboBox.setEditable(true);

        Label label = new Label(labelName);
        label.getStyleClass().add("mainbox-label");

        this.getChildren().addAll(label, comboBox);
    }



    @Override
    public Node getStyleableNode() {
        return this;
    }
}
