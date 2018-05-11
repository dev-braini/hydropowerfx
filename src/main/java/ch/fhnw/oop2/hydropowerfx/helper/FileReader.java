package ch.fhnw.oop2.hydropowerfx.helper;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.GroupedByCanton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    FileInputStream fileInputStream = null;
    BufferedReader  bufferedReader  = null;

    public void readPowerStations(String file, ObservableList<PowerStation> powerStationList) {
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + file);
            bufferedReader  = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    List<String> items = Arrays.asList(line.split(";"));
                    powerStationList.add(new PowerStation(items));
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException iOException) {
            iOException.printStackTrace();

        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                // bad
            }
        }
    }

    public void readCantons(String file, ObservableList<GroupedByCanton> powerStationList) {
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + file);
            bufferedReader  = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    List<String> items = Arrays.asList(line.split(";"));
                    powerStationList.add(new GroupedByCanton(items));
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException iOException) {
            iOException.printStackTrace();

        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                // bad
            }
        }
    }

}