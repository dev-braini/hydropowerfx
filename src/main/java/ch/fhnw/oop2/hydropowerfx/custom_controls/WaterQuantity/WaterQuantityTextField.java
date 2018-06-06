package ch.fhnw.oop2.hydropowerfx.custom_controls.WaterQuantity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;

public class WaterQuantityTextField extends TextField {
    private DoubleProperty quantityProperty = new SimpleDoubleProperty();

    public WaterQuantityTextField() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !newValue.isEmpty() && !checkValidValue(newValue)) {
                    setSuccessfullValue(newValue);

                } else {
                    resetToNull();
                }
            } catch (Exception exception) {
                setNotSuccessfullValue(newValue);

            }
        });

    }

    private boolean checkValidValue(String newValue){
        //wenn Wertebereich >=0
        if(newValue.equals(Integer.min(0, 6))){
            //if(newValue.equals(Double.parseDouble("0"))){

            return true;
        }
        return false;
    }

    private void setSuccessfullValue(String newValue){
        //wert weitergeben (successValue)
        double value = Double.valueOf(newValue);
        quantityProperty.setValue(value);
        setStyle("-fx-text-fill: black");
    }

    private void setNotSuccessfullValue(String newValue){
        //wert weitergeben (successValue)
        double value = Double.valueOf(0);
        quantityProperty.setValue(value);
        setStyle("-fx-text-fill: red");
    }

    private void resetToNull(){
        quantityProperty.setValue(0);
        setStyle("-fx-text-fill: pink");
    }

    public double getQuantityProperty() {
        return quantityProperty.get();
    }

    public DoubleProperty quantityPropertyProperty() {
        return quantityProperty;
    }

}
