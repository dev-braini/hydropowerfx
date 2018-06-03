package ch.fhnw.oop2.hydropowerfx.helper;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.GroupedByCanton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void readPowerStations() {
        //given
        FileHandler                  fileHandler      = new FileHandler();
        ObservableList<PowerStation> powerStationList = FXCollections.observableArrayList();

        //when
        fileHandler.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationList);

        //then
        assertTrue(powerStationList.get(0) instanceof PowerStation);
        assertTrue(powerStationList.get(0).getName() instanceof String);
    }

    @Test
    void readCantons() {
        //given
        FileHandler                     fileHandler     = new FileHandler();
        ObservableList<GroupedByCanton> groupedByCanton = FXCollections.observableArrayList();

        //when
        fileHandler.readCantons("/src/main/resources/data/cantons.csv", groupedByCanton);

        //then
        assertTrue(groupedByCanton.get(0) instanceof GroupedByCanton);
        assertTrue(groupedByCanton.get(0).getCantonName() instanceof String);
    }
}