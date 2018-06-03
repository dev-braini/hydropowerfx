package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.helper.FileHandler;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class RootPM {
    private FileHandler                                fileHandler                     = new FileHandler();

    private final StringProperty                       applicationTitle                = new SimpleStringProperty("HydroPowerFX");
    private final ObservableList<PowerStation>         powerStationList                = FXCollections.observableArrayList();
    private final List<ObservableList<PowerStation>>   powerStationListHistory         = new ArrayList<>();
    private final IntegerProperty                      powerStationListHistoryIndex    = new SimpleIntegerProperty(0);

    private final ObservableList<GroupedByCanton>      groupedByCanton                 = FXCollections.observableArrayList();
    private final ObservableList<GroupedByUsedWaters>  groupedByUsedWaters             = FXCollections.observableArrayList();
    private final ObservableList<GroupedByPerformance> groupedByPerformance            = FXCollections.observableArrayList();
    private final IntegerProperty                      powerStationTableSelectedIndex  = new SimpleIntegerProperty();
    private final StringProperty                       currentView                     = new SimpleStringProperty();

    private final IntegerProperty                      id                              = new SimpleIntegerProperty();
    private final StringProperty                       name                            = new SimpleStringProperty();
    private final StringProperty                       type                            = new SimpleStringProperty();
    private final StringProperty                       location                        = new SimpleStringProperty();
    private final StringProperty                       canton                          = new SimpleStringProperty();
    private final DoubleProperty                       waterVolume                     = new SimpleDoubleProperty();
    private final DoubleProperty                       performance                     = new SimpleDoubleProperty();
    private final IntegerProperty                      firstCommissioning              = new SimpleIntegerProperty();
    private final IntegerProperty                      lastCommissioning               = new SimpleIntegerProperty();
    private final DoubleProperty                       degreeOfLatitude                = new SimpleDoubleProperty();
    private final DoubleProperty                       degreeOfLongitude               = new SimpleDoubleProperty();
    private final StringProperty                       status                          = new SimpleStringProperty();
    private final StringProperty                       usedWaters                      = new SimpleStringProperty();
    private final StringProperty                       imageUrl                        = new SimpleStringProperty();

    private final BooleanProperty                      buttonNavControlSaveActive      = new SimpleBooleanProperty();
    private final BooleanProperty                      buttonNavControlUndoActive      = new SimpleBooleanProperty();
    private final BooleanProperty                      buttonNavControlRedoActive      = new SimpleBooleanProperty();
    private final BooleanProperty                      buttonNavControlRemoveActive    = new SimpleBooleanProperty();


    public RootPM() {
        fileHandler.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationList);
        fileHandler.readCantons("/src/main/resources/data/cantons.csv", groupedByCanton);

        ObservableList<PowerStation> powerStationListNew = FXCollections.observableArrayList();
        fileHandler.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationListNew);
        powerStationListHistory.add(powerStationListNew);
        powerStationListHistoryIndex.set(powerStationListHistory.size()-1);

        updateGroupedByCanton();
        updateGroupedByUsedWaters();
        initGroupedByPerformance();
        updateGroupedByPerformance();

    }

    public ObservableList<PowerStation> getPowerStationList() {
        return powerStationList;
    }

    public void savePowerStationList() {
        fileHandler.writePowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationList);

        ObservableList<PowerStation> powerStationListNew = FXCollections.observableArrayList();

        fileHandler.readPowerStations("/src/main/resources/data/HYDRO_POWERSTATION.csv", powerStationListNew);


        powerStationListHistory.add(powerStationListNew);

        powerStationListHistoryIndex.set(powerStationListHistory.size()-1);
        setButtonNavControlSaveActive(false);
        setButtonNavControlUndoActive(true);
    }

    public void undoPowerStationList() {
        powerStationListHistoryIndex.set(powerStationListHistoryIndex.get() - 1);

        int oldIndex = getPowerStationTableSelectedIndex();
        setPowerStationTableSelectedIndex(-1);

        powerStationList.removeAll(powerStationList);
        powerStationList.addAll(powerStationListHistory.get(powerStationListHistoryIndex.get()));

        setPowerStationTableSelectedIndex(oldIndex);
        setButtonNavControlRedoActive(true);
        setButtonNavControlSaveActive(true);

        if(powerStationListHistoryIndex.get() == 0) {
            setButtonNavControlUndoActive(false);
        }

        updateGroupedByCanton();
        updateGroupedByUsedWaters();
        updateGroupedByPerformance();
    }

    public void redoPowerStationList() {
        powerStationListHistoryIndex.set(powerStationListHistoryIndex.get() + 1);

        int oldIndex = getPowerStationTableSelectedIndex();
        setPowerStationTableSelectedIndex(-1);

        powerStationList.removeAll(powerStationList);
        powerStationList.addAll(powerStationListHistory.get(powerStationListHistoryIndex.get()));

        setPowerStationTableSelectedIndex(oldIndex);
        setButtonNavControlSaveActive(true);

        if(powerStationListHistoryIndex.get() == powerStationListHistory.size() - 1) setButtonNavControlRedoActive(false);
        if(powerStationListHistoryIndex.get() > 0) setButtonNavControlUndoActive(true);

        updateGroupedByCanton();
        updateGroupedByUsedWaters();
        updateGroupedByPerformance();

    }

    public List<PowerStation> getPowerStationListSortedFirstCommissioning() {
        return powerStationList
                .stream()
                .sorted((ps1, ps2) -> Long.compare(ps2.getFirstCommissioning(), ps1.getFirstCommissioning()))
                .collect(Collectors.toList());
    }

    public int getPowerStationIndex(PowerStation ps) {
        return getPowerStationListSortedFirstCommissioning().indexOf(ps);
    }

    public int getHighestIdFromPowerStationList() {
        return powerStationList
                .stream()
                .mapToInt(PowerStation::getId)
                .max().orElse(0);
    }

    public ObservableList<GroupedByCanton> getPowerStationListByCanton() {
        return groupedByCanton;
    }

    public ObservableList<GroupedByUsedWaters> getPowerStationListByUsedWaters() {
        return groupedByUsedWaters;
    }

    public ObservableList<GroupedByPerformance> getPowerStationListByPerformance() {
        return groupedByPerformance;
    }

    public int getPowerStationIndexByCanton(PowerStation ps) {
        for (int i = 0; i < groupedByCanton.size(); i++) {
            GroupedByCanton item = groupedByCanton.get(i);

            if (ps != null && item.getCantonShortcut().equals(ps.getCanton())) {
                return i;
            }
        }

        return 0;
    }

    public int getPowerStationIndexByUsedWaters(PowerStation ps) {
        for (int i = 0; i < groupedByUsedWaters.size(); i++) {
            GroupedByUsedWaters item = groupedByUsedWaters.get(i);

            if(ps != null && item.getUsedWaters().contains(ps.getUsedWaters() )) {
                return i;
            }
        }

        return -1;
    }

    public int getPowerStationIndexByPerformance(PowerStation ps) {
        for (int i = 0; i < groupedByPerformance.size(); i++) {
            GroupedByPerformance item = groupedByPerformance.get(i);

            if(ps != null && ps.getPerformance() >= item.getPerformanceMin() && ps.getPerformance() < item.getPerformanceMax()) {
                return i;
            }
        }

        return 0;
    }

    public void addToPowerStationList() {
        int id = getHighestIdFromPowerStationList() + 1;
        powerStationList.add(new PowerStation(id));
        setButtonNavControlSaveActive(true);
    }

    public void removeFromPowerStationList(int index) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation Dialog");
        alert.setHeaderText("Das ausgewählte Kraftwirklich wirklich löschen?");

        ButtonType buttonYes = new ButtonType("Ja, jetzt löschen");
        ButtonType buttonNo = new ButtonType("Nein");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            powerStationList.remove(index);
        }

        setButtonNavControlSaveActive(true);
    }

    // all getters and setters
    public Boolean getButtonNavControlSaveActive() {
        return buttonNavControlSaveActive.get();
    }

    public BooleanProperty buttonNavControlSaveActiveProperty() {
        return buttonNavControlSaveActive;
    }

    public void setButtonNavControlSaveActive(Boolean buttonNavControlSaveActive) {
        this.buttonNavControlSaveActive.set(buttonNavControlSaveActive);
    }


    public Boolean getButtonNavControlUndoActive() {
        return buttonNavControlUndoActive.get();
    }

    public BooleanProperty buttonNavControlUndoActiveProperty() {
        return buttonNavControlUndoActive;
    }

    public void setButtonNavControlUndoActive(Boolean buttonNavControlUndoActive) {
        this.buttonNavControlUndoActive.set(buttonNavControlUndoActive);
    }


    public Boolean getButtonNavControlRedoActive() {
        return buttonNavControlRedoActive.get();
    }

    public BooleanProperty buttonNavControlRedoActiveProperty() {
        return buttonNavControlRedoActive;
    }

    public void setButtonNavControlRedoActive(Boolean buttonNavControlRedoActive) {
        this.buttonNavControlRedoActive.set(buttonNavControlRedoActive);
    }


    public Boolean getButtonNavControlRemoveActive() {
        return buttonNavControlRemoveActive.get();
    }

    public BooleanProperty buttonNavControlRemoveActiveProperty() {
        return buttonNavControlRemoveActive;
    }

    public void setButtonNavControlRemoveActive(Boolean buttonNavControlRemoveActive) {
        this.buttonNavControlRemoveActive.set(buttonNavControlRemoveActive);
    }


    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }


    public String getCurrentView() {
        return currentView.get();
    }

    public StringProperty currentViewProperty() {
        return currentView;
    }

    public void setCurrentView(String view) {
        this.currentView.set(view);
    }


    public Integer getPowerStationTableSelectedIndex() {
        return powerStationTableSelectedIndex.get();
    }

    public IntegerProperty powerStationTableSelectedIndexProperty() {
        return powerStationTableSelectedIndex;

    }
    public void setPowerStationTableSelectedIndex(Integer powerStationTableSelectedIndex) {
        this.powerStationTableSelectedIndex.set(powerStationTableSelectedIndex);
    }


    public Integer         getId()                                           { return id.get();                                 }
    public IntegerProperty idProperty()                                      { return id;                                       }
    public void            setId(Integer id)                                 { this.id.set(id);                                 }

    public String          getName()                                         { return name.get();                               }
    public StringProperty  nameProperty()                                    { return name;                                     }
    public void            setName(String name)                              { this.name.set(name);                             }

    public String          getType()                                         { return type.get();                               }
    public StringProperty  typeProperty()                                    { return type;                                     }
    public void            setType(String type)                              { this.type.set(type);                             }

    public String          getLocation()                                     { return location.get();                           }
    public StringProperty  locationProperty()                                { return location;                                 }
    public void            setLocation(String location)                      { this.location.set(location);                     }

    public String          getCanton()                                       { return canton.get();                             }
    public StringProperty  cantonProperty()                                  { return canton;                                   }
    public void            setCanton(String canton)                          { this.canton.set(canton);                         }

    public Double          getWaterVolume()                                  { return waterVolume.get();                        }
    public DoubleProperty  waterVolumeProperty()                             { return waterVolume;                              }
    public void            setWaterVolume(Double waterVolume)                { this.waterVolume.set(waterVolume);               }

    public Double          getPerformance()                                  { return performance.get();                        }
    public DoubleProperty  performanceProperty()                             { return performance;                              }
    public void            setPerformance(Double performance)                { this.performance.set(performance);               }

    public Integer         getFirstCommissioning()                           { return firstCommissioning.get();                 }
    public IntegerProperty firstCommissioningProperty()                      { return firstCommissioning;                       }
    public void            setFirstCommissioning(Integer firstCommissioning) { this.firstCommissioning.set(firstCommissioning); }

    public Integer         getLastCommissioning()                            { return lastCommissioning.get();                  }
    public IntegerProperty lastCommissioningProperty()                       { return lastCommissioning;                        }
    public void            setLastCommissioning(Integer lastCommissioning)   { this.lastCommissioning.set(lastCommissioning);   }

    public Double          getDegreeOfLatitude()                             { return degreeOfLatitude.get();                   }
    public DoubleProperty  degreeOfLatitudeProperty()                        { return degreeOfLatitude;                         }
    public void            setDegreeOfLatitude(Double degreeOfLatitude)      { this.degreeOfLatitude.set(degreeOfLatitude);     }

    public Double          getDegreeOfLongitude()                            { return degreeOfLongitude.get();                  }
    public DoubleProperty  degreeOfLongitudeProperty()                       { return degreeOfLongitude;                        }
    public void            setDegreeOfLongitude(Double degreeOfLongitude)    { this.degreeOfLongitude.set(degreeOfLongitude);   }

    public String          getStatus()                                       { return status.get();                             }
    public StringProperty  statusProperty()                                  { return status;                                   }
    public void            setStatus(String status)                          { this.status.set(status);                         }

    public String          getUsedWaters()                                   { return usedWaters.get();                         }
    public StringProperty  usedWatersProperty()                              { return usedWaters;                               }
    public void            setUsedWaters(String usedWaters)                  { this.usedWaters.set(usedWaters);                 }

    public String          getImageUrl()                                     { return imageUrl.get();                           }
    public StringProperty  imageUrlProperty()                                { return imageUrl;                                 }
    public void            setImageUrl(String imageUrl)                      { this.imageUrl.set(imageUrl);                     }

    public void updateGroupedByCanton() {
        for (GroupedByCanton item : groupedByCanton) {
            item.setTotalPerformance(0.0);
            item.setNumberOfPowerStations(0);
        }

        for (PowerStation ps : powerStationList) {
            for (GroupedByCanton item : groupedByCanton) {
                if (item.getCantonShortcut().equals(ps.getCanton())) {
                    item.increaseNumberOfPowerStations();
                    item.increaseotalPerformance(ps.getPerformance());
                }
            }
        }
    }

    public void updateGroupedByUsedWaters() {
        groupedByUsedWaters.clear();

        for (PowerStation ps : powerStationList) {
            GroupedByUsedWaters newItem = new GroupedByUsedWaters(ps.getUsedWaters());

            if(!groupedByUsedWaters.contains(newItem)) {
                groupedByUsedWaters.add(newItem);
            }

            groupedByUsedWaters.get(groupedByUsedWaters.indexOf(newItem)).increaseNumberOfPowerStations();
            groupedByUsedWaters.get(groupedByUsedWaters.indexOf(newItem)).increaseotalPerformance(ps.getPerformance());

        }

        groupedByUsedWaters.removeIf(item -> (item.getNumberOfPowerStations() < 3));
    }

    public void initGroupedByPerformance() {
        groupedByPerformance.clear();

        groupedByPerformance.add(new GroupedByPerformance("    0   -   10", 0.0, 10.0));
        groupedByPerformance.add(new GroupedByPerformance("  10   -   20", 10.0, 20.0));
        groupedByPerformance.add(new GroupedByPerformance("  20   -   30", 20.0, 30.0));
        groupedByPerformance.add(new GroupedByPerformance("  30   -   40", 30.0, 40.0));
        groupedByPerformance.add(new GroupedByPerformance("  40   -   50", 40.0, 50.0));
        groupedByPerformance.add(new GroupedByPerformance("  50   -   60", 50.0, 60.0));
        groupedByPerformance.add(new GroupedByPerformance("  60   -   70", 60.0, 70.0));
        groupedByPerformance.add(new GroupedByPerformance("  70   -   80", 70.0, 80.0));
        groupedByPerformance.add(new GroupedByPerformance("  80   -   90", 80.0, 90.0));
        groupedByPerformance.add(new GroupedByPerformance("  90   - 100", 90.0, 100.0));
        groupedByPerformance.add(new GroupedByPerformance("100   - 150", 100.0, 150.0));
        groupedByPerformance.add(new GroupedByPerformance("150   - 200", 150.0, 200.0));
        groupedByPerformance.add(new GroupedByPerformance("         > 200", 200.0, Double.MAX_VALUE));
    }

    public void updateGroupedByPerformance() {
        for (GroupedByPerformance item : groupedByPerformance) {
            item.setTotalPerformance(0.0);
            item.setNumberOfPowerStations(0);
        }

        for (PowerStation ps : powerStationList) {
            Double itemPerformance = ps.getPerformance();

            for (GroupedByPerformance item : groupedByPerformance) {
                if(itemPerformance >= item.getPerformanceMin() && itemPerformance < item.getPerformanceMax()) {
                    item.increaseNumberOfPowerStations();
                    item.increaseotalPerformance(itemPerformance);
                }
            }
        }
    }

    public void updatePowerStation(int index) {
        PowerStation selectedPS = getPowerStationList().get(index);

        //set save button active only if something really changed
        if(
            selectedPS.getId() != id.get() ||
            selectedPS.getName() != name.get() ||
            selectedPS.getType() != type.get() ||
            selectedPS.getLocation() != location.get() ||
            selectedPS.getCanton() != canton.get() ||
            selectedPS.getWaterVolume() != waterVolume.get() ||
            selectedPS.getPerformance() != performance.get() ||
            selectedPS.getFirstCommissioning() != firstCommissioning.get() ||
            selectedPS.getLastCommissioning() != lastCommissioning.get() ||
            selectedPS.getDegreeOfLatitude() != degreeOfLatitude.get() ||
            selectedPS.getDegreeOfLongitude() != degreeOfLongitude.get() ||
            selectedPS.getStatus() != status.get() ||
            selectedPS.getUsedWaters() != usedWaters.get() ||
            selectedPS.getImageUrl() != imageUrl.get()
        ) {
            setButtonNavControlSaveActive(true);
        }

        selectedPS.setId(id.get());
        selectedPS.setName(name.get());
        selectedPS.setType(type.get());
        selectedPS.setLocation(location.get());
        selectedPS.setCanton(canton.get());
        selectedPS.setWaterVolume(waterVolume.get());
        selectedPS.setPerformance(performance.get());
        selectedPS.setFirstCommissioning(firstCommissioning.get());
        selectedPS.setLastCommissioning(lastCommissioning.get());
        selectedPS.setDegreeOfLatitude(degreeOfLatitude.get());
        selectedPS.setDegreeOfLongitude(degreeOfLongitude.get());
        selectedPS.setStatus(status.get());
        selectedPS.setUsedWaters(usedWaters.get());
        selectedPS.setImageUrl(imageUrl.get());

        updateGroupedByCanton();
        updateGroupedByUsedWaters();
        updateGroupedByPerformance();
    }

    public void showPowerStationDetails(PowerStation powerStation) {
        if (powerStation != null) {
            // Fill the labels with info from the powerStation object.
            id.setValue(powerStation.getId());
            name.setValue(powerStation.getName());
            type.setValue(powerStation.getType());
            location.setValue(powerStation.getLocation());
            canton.setValue(powerStation.getCanton());
            waterVolume.setValue(powerStation.getWaterVolume());
            performance.setValue(powerStation.getPerformance());
            firstCommissioning.setValue(powerStation.getFirstCommissioning());
            lastCommissioning.setValue(powerStation.getLastCommissioning());
            degreeOfLatitude.setValue(powerStation.getDegreeOfLatitude());
            degreeOfLongitude.setValue(powerStation.getDegreeOfLongitude());
            status.setValue(powerStation.getStatus());
            usedWaters.setValue(powerStation.getUsedWaters());
            imageUrl.setValue(powerStation.getImageUrl());
        } else {
            // PowerStation is null, remove all the values.
            id.setValue(0);
            name.setValue("");
            type.setValue("");
            location.setValue("");
            canton.setValue("");
            waterVolume.setValue(0);
            performance.setValue(0);
            firstCommissioning.setValue(0);
            lastCommissioning.setValue(0);
            degreeOfLatitude.setValue(0);
            degreeOfLongitude.setValue(0);
            status.setValue("");
            usedWaters.setValue("");
            imageUrl.setValue(null);
        }
    }
}
