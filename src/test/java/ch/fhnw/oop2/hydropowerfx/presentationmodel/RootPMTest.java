package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.helper.FileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RootPMTest {

    @Test
    void getPowerStationList() {
        //given
        RootPM                          rootPM           = new RootPM();
        FileHandler                     fileHandler      = new FileHandler();
        ObservableList<PowerStation>    powerStationList = FXCollections.observableArrayList();
        ObservableList<GroupedByCanton> groupedByCanton  = FXCollections.observableArrayList();

        //when
        fileHandler.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationList);
        fileHandler.readCantons("/src/main/resources/data/cantons.csv", groupedByCanton);

        //then
        assertTrue(rootPM.getPowerStationList().get(0) instanceof PowerStation);
        assertTrue(rootPM.getPowerStationList().get(0).getName() instanceof String);
    }
}