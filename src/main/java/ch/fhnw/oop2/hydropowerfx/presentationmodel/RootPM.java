package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import ch.fhnw.oop2.hydropowerfx.helper.FileReader;
import ch.fhnw.oop2.hydropowerfx.model.PowerStation;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RootPM {

    private final StringProperty                applicationTitle   = new SimpleStringProperty("HydroPowerFX");
    private final StringProperty                greeting           = new SimpleStringProperty("Hello World!");
    private final ObservableList<PowerStation>  powerStationList   = FXCollections.observableArrayList();

    private final IntegerProperty               id                 = new SimpleIntegerProperty();
    private final StringProperty                name               = new SimpleStringProperty();
    private final StringProperty                type               = new SimpleStringProperty();
    private final StringProperty                location           = new SimpleStringProperty();
    private final StringProperty                canton             = new SimpleStringProperty();
    private final DoubleProperty                waterVolume        = new SimpleDoubleProperty();
    private final DoubleProperty                performance        = new SimpleDoubleProperty();
    private final IntegerProperty               firstCommissioning = new SimpleIntegerProperty();
    private final IntegerProperty               lastCommissioning  = new SimpleIntegerProperty();
    private final DoubleProperty                degreeOfLatitude   = new SimpleDoubleProperty();
    private final DoubleProperty                degreeOfLongitude  = new SimpleDoubleProperty();
    private final StringProperty                status             = new SimpleStringProperty();
    private final StringProperty                usedWaters         = new SimpleStringProperty();
    private final StringProperty                imageUrl           = new SimpleStringProperty();

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
