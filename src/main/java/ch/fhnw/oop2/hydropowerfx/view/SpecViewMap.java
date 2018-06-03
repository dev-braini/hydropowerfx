package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;

/*
 * @author: Marco Peter & Markus Winter
 */
public class SpecViewMap extends VBox implements ViewMixin {
    private final RootPM              rootPM;

    private MyBrowser                 myBrowser;
    private PowerStationInfoBox       infoBox;

    public SpecViewMap(RootPM model) {
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
        myBrowser = new MyBrowser(rootPM);
        infoBox = new PowerStationInfoBox(rootPM);
    }

    @Override
    public void layoutControls() {
        infoBox.getStyleClass().add("default-padding");
        this.getChildren().addAll(infoBox, myBrowser);
    }

    @Override
    public void setupBindings() {
        rootPM.degreeOfLongitudeProperty().addListener(observable -> myBrowser.setCoordinates());
        rootPM.degreeOfLatitudeProperty().addListener(observable -> myBrowser.setCoordinates());
        rootPM.currentViewProperty().addListener(observable -> myBrowser.setCoordinates());
    }

    class MyBrowser extends VBox {
        private final RootPM rootPM;
        private WebView webView = new WebView();
        private WebEngine webEngine = webView.getEngine();

        public MyBrowser(RootPM rootPM) {
            this.rootPM = rootPM;
            final URL urlGoogleMaps = getClass().getResource("GoogleMaps.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
            /*webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> e) {
                    System.out.println(e.toString());
                }
            });*/


            getChildren().add(webView);
        }

        public void setCoordinates() {
            System.out.println(rootPM.getName());
            webEngine.executeScript("" +
                    "window.lat = " + rootPM.getDegreeOfLatitude() + ";" +
                    "window.lon = " + rootPM.getDegreeOfLongitude() + ";" +
                    "window.markerName = \"" + rootPM.getName() + "\";" +
                    "document.goToLocation(window.lat, window.lon, window.markerName);"
            );
        }
    }
}
