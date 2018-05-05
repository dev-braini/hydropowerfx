package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.helper.FileReader;
import ch.fhnw.oop2.hydropowerfx.model.PowerStation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RootPM {

    private final StringProperty                applicationTitle  = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty                greeting          = new SimpleStringProperty("Hello World!");
    private final ObservableList<PowerStation>  powerStationList  = FXCollections.observableArrayList();

    public RootPM() {
        FileReader fileReader = new FileReader();
        fileReader.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationList);
    }

    public ObservableList<PowerStation> getPowerStationList() {
        return powerStationList;
    }

    // all getters and setters
    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public String getGreeting() {
        return greeting.get();
    }

    public StringProperty greetingProperty() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting.set(greeting);
    }
}
