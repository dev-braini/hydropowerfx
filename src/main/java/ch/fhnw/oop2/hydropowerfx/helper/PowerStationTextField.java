package ch.fhnw.oop2.hydropowerfx.helper;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PowerStationTextField extends HBox {
    private TextField textField = new TextField();
    private TableView powerStationTable;

    public PowerStationTextField(String labelName, TableView powerStationTable, RootPM rootPM, boolean... numeric) {
        this.setOnKeyReleased(event -> {
            rootPM.updatePowerStation(
                    powerStationTable.getSelectionModel().getSelectedIndex()
            );
        });

        boolean isNumeric = numeric.length > 0 ? numeric[0] : false;

        if(isNumeric) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("([0-9]+([.][0-9]*)?|[.][0-9]+)")) {
                        textField.setText(oldValue);
                    }
                }
            });
        }

        Label label = new Label(labelName);
        label.getStyleClass().add("mainbox-label");

        this.getChildren().addAll(label, textField);
    }

    @Override
    public Node getStyleableNode() {
        return this;
    }

    public TextField getTextField() {
        return textField;
    }
}
