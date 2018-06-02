package ch.fhnw.oop2.hydropowerfx.helper;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.GroupedByCanton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileHandler {

    private FileInputStream     fileInputStream     = null;
    private BufferedReader      bufferedReader      = null;
    private FileWriter          fileWriter          = null;
    private static final String SEMICOLON_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR  = "\n";

    public void readPowerStations(String fileName, ObservableList<PowerStation> powerStationList) {
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir") + fileName);
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

    public void writePowerStations(String fileName, ObservableList<PowerStation> powerStationList) {
        String FILE_HEADER = "ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL";

        try {
            //fileWriter = new FileWriter(fileName);
            fileWriter = new FileWriter(System.getProperty("user.dir") + fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (PowerStation ps : powerStationList) {
                fileWriter.append(String.valueOf(ps.getId()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getName());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getType());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getLocation());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getCanton());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getWaterVolume()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getPerformance()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getFirstCommissioning()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getLastCommissioning()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getDegreeOfLatitude()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(String.valueOf(ps.getDegreeOfLongitude()));
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getStatus());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(ps.getUsedWaters());
                fileWriter.append(SEMICOLON_DELIMITER);
                fileWriter.append(Objects.toString(ps.getImageUrl(), ""));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
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