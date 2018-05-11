package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.*;

/**
 * Model class for a PowerStation.
 *
 * @author Marco Peter & Markus Winter
 */
public class GroupedByPerformance {
    private final StringProperty   performanceRange;
    private final DoubleProperty   performanceMin;
    private final DoubleProperty   performanceMax;
    private final DoubleProperty   totalPerformance;
    private final IntegerProperty  numberOfPowerStations;

    /**
     * Constructor with some initial data.
     *
     * @param performanceRange
     * @param performanceMin
     * @param performanceMax
     * @param totalPerformance
     * @param numberOfPowerStations
     */
    public GroupedByPerformance (
            String performanceRange,
            double performanceMin,
            double performanceMax,
            double totalPerformance,
            int    numberOfPowerStations
    ) {
        this.performanceRange      = new SimpleStringProperty(performanceRange);
        this.performanceMin        = new SimpleDoubleProperty(performanceMin);
        this.performanceMax        = new SimpleDoubleProperty(performanceMax);
        this.totalPerformance      = new SimpleDoubleProperty(totalPerformance);
        this.numberOfPowerStations = new SimpleIntegerProperty(numberOfPowerStations);
    }

    public GroupedByPerformance (
            String performanceRange,
            Double performanceMin,
            Double performanceMax
    ) {
        this(performanceRange, performanceMin, performanceMax, 0.0, 0);
    }

    //getters & setters

    public String getPerformanceRange() {
        return performanceRange.get();
    }

    public StringProperty performanceRangeProperty() {
        return performanceRange;
    }

    public void setPerformanceRange(String performanceRange) {
        this.performanceRange.set(performanceRange);
    }


    public Double getPerformanceMin() {
        return performanceMin.get();
    }

    public DoubleProperty performanceMinProperty() {
        return performanceMin;
    }

    public void setPerformanceMin(Double performanceMin) {
        this.performanceMin.set(performanceMin);
    }


    public Double getPerformanceMax() {
        return performanceMax.get();
    }

    public DoubleProperty performanceMaxProperty() {
        return performanceMax;
    }

    public void setPerformanceMax(Double performanceMax) {
        this.performanceMax.set(performanceMax);
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
                ? getTotalPerformance().equals(((GroupedByPerformance)o).getTotalPerformance())
                : super.equals(o); }
}