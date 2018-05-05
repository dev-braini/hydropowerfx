package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.helper.PowerStationInfoBox;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import java.net.URL;

/*
 * @author: Marco Peter & Markus Winter
 */
public class MapView extends VBox implements ViewMixin {
    private final RootPM rootPM;

    private MyBrowser myBrowser;
    private PowerStationInfoBox infoBox;
    private double lat;
    private double lon;
    private EventHandler<ActionEvent> customEvent;

    public MapView(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("main-content-right-map");
        this.addStylesheetFiles("../css/MapView.css");
    }

    @Override
    public void initializeControls() {
        myBrowser = new MyBrowser();
        infoBox = new PowerStationInfoBox();
    }

    @Override
    public void layoutControls() {
        infoBox.getStyleClass().add("default-padding");
        this.getChildren().addAll(infoBox, myBrowser);
    }

    @Override
    public void setupBindings() {


    }

    class MyBrowser extends VBox {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        public MyBrowser() {
            final URL urlGoogleMaps = getClass().getResource("GoogleMaps.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });


            getChildren().add(webView);

            final TextField latitude = new TextField("" + 47.481409);
            final TextField longitude = new TextField("" + 8.211644);
            Button update = new Button("Update");

            customEvent = e -> {
                lat = Double.parseDouble(latitude.getText());
                lon = Double.parseDouble(longitude.getText());

                System.out.printf("%.2f %.2f%n", lat, lon);

                webEngine.executeScript("" +
                        "window.lat = " + lat + ";" +
                        "window.lon = " + lon + ";" +
                        "document.goToLocation(window.lat, window.lon);"
                );
            };

            update.setOnAction(customEvent);

            HBox toolbar  = new HBox();
            toolbar.getChildren().addAll(latitude, longitude, update);

            getChildren().add(toolbar);
        }


    }
}
