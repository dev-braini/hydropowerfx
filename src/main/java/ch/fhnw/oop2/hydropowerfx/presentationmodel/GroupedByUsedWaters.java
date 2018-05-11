package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.*;

/**
 * Model class for a PowerStation.
 *
 * @author Marco Peter & Markus Winter
 */
public class GroupedByUsedWaters {
    private final StringProperty   usedWaters;
    private final DoubleProperty   totalPerformance;
    private final IntegerProperty  numberOfPowerStations;

    /**
     * Constructor with some initial data.
     *
     * @param usedWaters
     * @param totalPerformance
     * @param numberOfPowerStations
     */
    public GroupedByUsedWaters(
            String usedWaters,
            double totalPerformance,
            int    numberOfPowerStations
    ) {
        this.usedWaters            = new SimpleStringProperty(usedWaters);
        this.totalPerformance      = new SimpleDoubleProperty(totalPerformance);
        this.numberOfPowerStations = new SimpleIntegerProperty(numberOfPowerStations);
    }

    public GroupedByUsedWaters(String usedWaters) {
        this(usedWaters, 0.0, 0);
    }

    //getters & setters

    public String getUsedWaters() {
        return usedWaters.get();
    }

    public StringProperty usedWatersProperty() {
        return usedWaters;
    }

    public void setUsedWaters(String usedWaters) {
        this.usedWaters.set(usedWaters);
    }


    public void increaseotalPerformance(double value) {
        double n = totalPerformance.get();
        n += value;
        this.totalPerformance.set(n);
    }
    public Double getTotalPerformance() {
        return totalPerformance.get();
    }

    public DoubleProperty totalPerformanceProperty() {
        return totalPerformance;
    }

    public void setTotalPerformance(Double totalPerformance) {
        this.totalPerformance.set(totalPerformance);
    }


    public void increaseNumberOfPowerStations() {
        int n = numberOfPowerStations.get();
        n++;
        this.numberOfPowerStations.set(n);
    }

    public Integer getNumberOfPowerStations() {
        return numberOfPowerStations.get();
    }

    public IntegerProperty numberOfPowerStationsProperty() {
        return numberOfPowerStations;
    }

    public void setNumberOfPowerStations(Integer numberOfPowerStations) {
        this.numberOfPowerStations.set(numberOfPowerStations);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof GroupedByUsedWaters)
                ? getUsedWaters().equals(((GroupedByUsedWaters)o).getUsedWaters())
                : super.equals(o); }
}