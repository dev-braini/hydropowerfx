package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import java.util.List;

import javafx.beans.property.*;

/**
 * Model class for a PowerStation.
 *
 * @author Marco Peter & Markus Winter
 */
public class PowerStation {
    private final IntegerProperty  id;
    private final StringProperty   name;
    private final StringProperty   type;
    private final StringProperty   location;
    private final StringProperty   canton;
    private final DoubleProperty   waterVolume;
    private final DoubleProperty   performance;
    private final IntegerProperty  firstCommissioning;
    private final IntegerProperty  lastCommissioning;
    private final DoubleProperty   degreeOfLatitude;
    private final DoubleProperty   degreeOfLongitude;
    private final StringProperty   status;
    private final StringProperty   usedWaters;
    private final StringProperty   imageUrl;

    /**
     * Default constructor.
     */
    public PowerStation(int id) {
        this(   id,
                "",
                "",
                "",
                "",
                0,
                0,
                0,
                0,
                0,
                0,
                "",
                "",
                ""
        );
    }

    /**
     * Default constructor.
     */
    public PowerStation(List<String> data) {
        this(   (int)     Integer.parseInt(data.get(0)),
                (String)  data.get(1),
                (String)  data.get(2),
                (String)  data.get(3),
                (String)  data.get(4),
                (double)  Double.parseDouble(data.get(5)),
                (double)  Double.parseDouble(data.get(6)),
                (int)     Integer.parseInt(data.get(7)),
                (int)     Integer.parseInt(data.get(8)),
                (double)  Double.parseDouble(data.get(9)),
                (double)  Double.parseDouble(data.get(10)),
                (String)  data.get(11),
                (String)  data.get(12),
                (data.size() > 13) ? (String) data.get(13) : null
        );
    }

    /**
     * Constructor with some initial data.
     *
     * @param id
     * @param name
     * @param type
     * @param location
     * @param canton
     * @param waterVolume
     * @param performance
     * @param firstCommissioning
     * @param lastCommissioning
     * @param degreeOfLatitude
     * @param degreeOfLongitude
     * @param status
     * @param usedWaters
     * @param imageUrl
     */
    public PowerStation(
            int  id,
            String   name,
            String   type,
            String   location,
            String   canton,
            double   waterVolume,
            double   performance,
            int      firstCommissioning,
            int      lastCommissioning,
            double   degreeOfLatitude,
            double   degreeOfLongitude,
            String   status,
            String   usedWaters,
            String   imageUrl
    ) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.location = new SimpleStringProperty(location);
        this.canton = new SimpleStringProperty(canton);
        this.waterVolume = new SimpleDoubleProperty(waterVolume);
        this.performance = new SimpleDoubleProperty(performance);
        this.firstCommissioning = new SimpleIntegerProperty(firstCommissioning);
        this.lastCommissioning = new SimpleIntegerProperty(lastCommissioning);
        this.degreeOfLatitude = new SimpleDoubleProperty(degreeOfLatitude);
        this.degreeOfLongitude = new SimpleDoubleProperty(degreeOfLongitude);
        this.status = new SimpleStringProperty(status);
        this.usedWaters = new SimpleStringProperty(usedWaters);
        this.imageUrl = new SimpleStringProperty(imageUrl);
    }

    //getters & setters

    public int getId() {
        return id.get();
    }

    public int idProperty() {
        return id.get();
    }

    public int setId(Integer id) {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getCanton() {
        return canton.get();
    }

    public StringProperty cantonProperty() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton.set(canton);
    }

    public double getWaterVolume() {
        return waterVolume.get();
    }

    public DoubleProperty waterVolumeProperty() {
        return waterVolume;
    }

    public void setWaterVolume(double waterVolume) {
        this.waterVolume.set(waterVolume);
    }

    public double getPerformance() {
        return performance.get();
    }

    public DoubleProperty performanceProperty() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance.set(performance);
    }

    public int getFirstCommissioning() {
        return firstCommissioning.get();
    }

    public IntegerProperty firstCommissioningProperty() {
        return firstCommissioning;
    }

    public void setFirstCommissioning(int firstCommissioning) {
        this.firstCommissioning.set(firstCommissioning);
    }

    public int getLastCommissioning() {
        return lastCommissioning.get();
    }

    public IntegerProperty lastCommissioningProperty() {
        return lastCommissioning;
    }

    public void setLastCommissioning(int lastCommissioning) {
        this.lastCommissioning.set(lastCommissioning);
    }

    public double getDegreeOfLatitude() {
        return degreeOfLatitude.get();
    }

    public DoubleProperty degreeOfLatitudeProperty() {
        return degreeOfLatitude;
    }

    public void setDegreeOfLatitude(double degreeOfLatitude) {
        this.degreeOfLatitude.set(degreeOfLatitude);
    }

    public double getDegreeOfLongitude() {
        return degreeOfLongitude.get();
    }

    public DoubleProperty degreeOfLongitudeProperty() {
        return degreeOfLongitude;
    }

    public void setDegreeOfLongitude(double degreeOfLongitude) {
        this.degreeOfLongitude.set(degreeOfLongitude);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getUsedWaters() {
        return usedWaters.get();
    }

    public StringProperty usedWatersProperty() {
        return usedWaters;
    }

    public void setUsedWaters(String usedWaters) {
        this.usedWaters.set(usedWaters);
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
}