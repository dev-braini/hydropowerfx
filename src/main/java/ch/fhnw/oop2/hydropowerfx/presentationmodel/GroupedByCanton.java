package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.*;

import java.util.List;

/**
 * Model class for a PowerStation.
 *
 * @author Markus Winter
 */
public class GroupedByCanton {
    private final IntegerProperty  id;
    private final StringProperty   cantonShortcut;
    private final StringProperty   cantonName;
    private final DoubleProperty   totalPerformance;
    private final IntegerProperty  numberOfPowerStations;

    /**
     * Default constructor.
     */
    public GroupedByCanton(List<String> data) {
        this(   (int)     Integer.parseInt(data.get(2)),
                (String)  data.get(1),
                (String)  data.get(0)
        );
    }

    /**
     * Constructor with some initial data.
     *
     * @param id
     * @param cantonShortcut
     * @param cantonName
     */
    public GroupedByCanton(
            int      id,
            String   cantonShortcut,
            String   cantonName
    ) {
        this.id                    = new SimpleIntegerProperty(id);
        this.cantonShortcut        = new SimpleStringProperty(cantonShortcut);
        this.cantonName            = new SimpleStringProperty(cantonName);
        this.totalPerformance      = new SimpleDoubleProperty();
        this.numberOfPowerStations = new SimpleIntegerProperty();
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



    public String getCantonShortcut() {
        return cantonShortcut.get();
    }

    public StringProperty cantonShortcutProperty() {
        return cantonShortcut;
    }

    public void setCantonShortcut(String name) {
        this.cantonShortcut.set(name);
    }



    public String getCantonName() {
        return cantonName.get();
    }

    public StringProperty cantonNameProperty() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName.set(cantonName);
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
}