package ch.fhnw.oop2.hydropowerfx.custom_controls.SwissLocation;

import javafx.animation.TranslateTransition;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * ToDo: CustomControl kurz beschreiben
 *
 * Kanton kann über die Schweizerkarte ausgewählt werden.
 * Zusätzlich wird ein Marker an den gewählten Koordinaten angezeigt.
 *
 * @author Benjamin Denzler, Dario Lohmuller
 */
public class SwissLocationControl extends Region {

    private static final PseudoClass CHOSEN_CLASS = PseudoClass.getPseudoClass("on_chosen");

    //Styleclasses
    private static final String CUSTOM_CONTROL_STYLECLASS = "swissLocationControl";
    private static final String COORDINATE_LABEL_STYLECLASS = "coordinate-label";
    private static final String KANTON_LABEL_STYLECLASS = "kanton-label";
    private static final String COAT_OF_ARMS_STYLECLASS = "coat-of-arms";
    private static final String LOCATION_MARKER_STYLECLASS = "location-marker";
    private static final String DRAWING_PANE_STYLECLASS = "drawing-pane";
    private static final String KANTON_STYLECLASS = "kanton";

    private static final String OPACITY_RECTANGLE = "opacity-rectangle";

    //Assets
    private static final String FONTS_PATH = "fonts/fonts.css";
    private static final String STYLESHEET_PATH = "swissLocationControl/style.css";
    private static final String MAP_MARKER_IMAGE_PATH = "swissLocationControl/map-marker.png";
    private static final String COAT_OF_ARMS_FOLDER_PATH = "swissLocationControl/wappen/";

    private static final double ARTBOARD_WIDTH  = 80;
    private static final double ARTBOARD_HEIGHT = 55;

    private static final double WESTBORDER = 5.956532;
    private static final double NORTHBORDER = 47.808048;
    private static final double EASTBORDER = 10.492377;
    private static final double SOUTHBORDER = 45.817216;

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 300;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;

    private static final double MAXIMUM_WIDTH = 1000;

    // Todo: diese Parts durch alle notwendigen Parts der gewünschten CustomControl ersetzen
    private static ResourceBundle SVG_KANTONE = ResourceBundle.getBundle("ch.fhnw.oop2.hydropowerfx.custom_controls.SwissLocation.swissLocationControl.svgKantone");

    private Label longitudeLabel, latitudeLabel, kantonLabel;
    private ImageView coatOfArms, locationMarker;

    private Rectangle opacityRectangle;

    private Group map;

    private TranslateTransition transition;

    private double scaleX;
    private double scaleY;
    private double offsetX;
    private double offsetY;
    private boolean clicked;

    //Properties for public API
    private final StringProperty kanton = new SimpleStringProperty();
    private final DoubleProperty longitude = new SimpleDoubleProperty();
    private final DoubleProperty latitude = new SimpleDoubleProperty();
    private final BooleanProperty longitudeFocus = new SimpleBooleanProperty();
    private final BooleanProperty latitudeFocus = new SimpleBooleanProperty();

    private final DoubleProperty locationMarkerX = new SimpleDoubleProperty();
    private final DoubleProperty locationMarkerY = new SimpleDoubleProperty();

    // needed for resizing
    private Pane drawingPane;
    private Rectangle clip;

    public SwissLocationControl() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        initializeAnimation();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        setMaxSize(MAXIMUM_WIDTH, MAXIMUM_WIDTH/ASPECT_RATIO);
        setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);

        // load stylesheets
        String fonts = getClass().getResource(FONTS_PATH).toExternalForm();
        getStylesheets().add(fonts);

        String stylesheet = getClass().getResource(STYLESHEET_PATH).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add(CUSTOM_CONTROL_STYLECLASS);
    }

    private void initializeParts() {

        latitudeLabel = createRightAlignedLabel(20, 3, COORDINATE_LABEL_STYLECLASS);

        longitudeLabel = createRightAlignedLabel(20, 6, COORDINATE_LABEL_STYLECLASS);

        coatOfArms = new ImageView();
        coatOfArms.getStyleClass().add(COAT_OF_ARMS_STYLECLASS);
        coatOfArms.setFitWidth(10);
        coatOfArms.setPreserveRatio(true);
        coatOfArms.setLayoutX(ARTBOARD_WIDTH-(coatOfArms.getFitWidth()+3));
        coatOfArms.setLayoutY(3);

        kantonLabel = createRightAlignedLabel(coatOfArms.getLayoutX()-2, 3, KANTON_LABEL_STYLECLASS);

        locationMarker = new ImageView(new Image(getClass().getResource(MAP_MARKER_IMAGE_PATH).toExternalForm()));
        locationMarker.getStyleClass().add(LOCATION_MARKER_STYLECLASS);
        locationMarker.setFitWidth(3.5);
        locationMarker.setFitHeight(4.7);
        locationMarker.setVisible(false);
        locationMarker.setMouseTransparent(true);

        opacityRectangle = new Rectangle();
        opacityRectangle.getStyleClass().add(OPACITY_RECTANGLE);
        opacityRectangle.setHeight(5.2);
        opacityRectangle.setWidth(25);
        opacityRectangle.setLayoutX(ARTBOARD_WIDTH-(coatOfArms.getFitWidth()+ 30));
        opacityRectangle.setLayoutY(2);

        locationMarker.setCache(true);
        locationMarker.setCacheHint(CacheHint.QUALITY);
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add(DRAWING_PANE_STYLECLASS);
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);

        clip = new Rectangle(ARTBOARD_WIDTH,ARTBOARD_HEIGHT);
        drawingPane.setClip(clip);
    }

    private void layoutParts() {
        // ToDo: alle Parts zur drawingPane hinzufügen
        map = new Group();
        for(Kantone k : Kantone.values())
            map.getChildren().add(k.svgPath);

        map.setScaleX(0.06);
        map.setScaleY(0.06);
        map.setTranslateX(-572);
        map.setTranslateY(-802);

        scaleX = ((EASTBORDER-WESTBORDER)/(map.localToParent(map.getLayoutBounds()).getMaxX()-map.localToParent(map.getLayoutBounds()).getMinX()));
        scaleY = ((NORTHBORDER-SOUTHBORDER)/(map.localToParent(map.getLayoutBounds()).getMaxY()-map.localToParent(map.getLayoutBounds()).getMinY()));
        offsetX = map.localToParent(map.getLayoutBounds()).getMinX()-(locationMarker.getFitWidth()/2);
        offsetY = map.localToParent(map.getLayoutBounds()).getMinY()-locationMarker.getFitHeight();

        drawingPane.getChildren().addAll(map, locationMarker, opacityRectangle, longitudeLabel, latitudeLabel, kantonLabel, coatOfArms);
        getChildren().add(drawingPane);
    }

    private void initializeAnimation(){
        Duration duration = Duration.millis(500);
        transition = new TranslateTransition(duration, locationMarker);
    }

    private void setupEventHandlers() {
        for (Kantone k : Kantone.values()){
            k.svgPath.setOnMouseClicked(event -> {

                switch (event.getButton()){
                    case PRIMARY:
                        Point2D p = new Point2D(event.getX(), event.getY());
                        clicked = true;
                        setLongitude((map.localToParent(p).getX()-(offsetX+locationMarker.getFitWidth()/2))*scaleX+WESTBORDER);
                        setLatitude(-(map.localToParent(p).getY()-(offsetY+locationMarker.getFitHeight()))*scaleY+NORTHBORDER);
                        break;
                    case SECONDARY:
                        colorKanton(k);
                        setKanton(k.toString());
                        break;
                }
            });

            k.svgPath.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    kantonLabel.setText(k.name);
                    coatOfArms.setImage(k.coatOfArms);
                } else {
                    if (getKanton() != null && !getKanton().equals("")){
                        try{
                            kantonLabel.setText(Kantone.valueOf(getKanton().toUpperCase()).name);
                            coatOfArms.setImage(Kantone.valueOf(getKanton().toUpperCase()).coatOfArms);
                        }catch (Exception e){
                            kantonLabel.setText("");
                            coatOfArms.setImage(null);
                        }
                    }
                    if(getKanton() == null || getKanton().equals("")){
                        kantonLabel.setText("");
                        coatOfArms.setImage(null);
                    }
                }
            });
        }

        drawingPane.setOnMouseMoved(event -> {
            Point2D p = new Point2D(event.getX(), event.getY());
            double xMouse = event.getX();
            double yMouse = event.getY();

            double widthMarker = locationMarker.getFitWidth();
            double heightMarker = locationMarker.getFitHeight();
            double xMarker = getLocationMarkerX();
            double yMarker = getLocationMarkerY();

            if (xMouse >= xMarker && xMouse <= xMarker+widthMarker && yMouse >= yMarker && yMouse <= yMarker+heightMarker){
                locationMarker.setOpacity(0.5);
            }else{
                locationMarker.setOpacity(1);
            }
        });
    }

    private void setupValueChangeListeners() {

        longitudeProperty().addListener((observable, oldValue, newValue) -> {
            longitudeLabel.setText(doubleToSexagesimal(newValue.doubleValue(), true));
            setLocationMarkerX(((newValue.doubleValue()-WESTBORDER)/scaleX)+offsetX);
            boolean isOnMap = markerIsOnMap();
            longitudeLabel.setVisible(isOnMap);
            latitudeLabel.setVisible(isOnMap);
        });
        latitudeProperty().addListener((observable, oldValue, newValue) -> {
            latitudeLabel.setText(doubleToSexagesimal(newValue.doubleValue(), false));
            setLocationMarkerY(-((newValue.doubleValue()-NORTHBORDER)/scaleY)+offsetY);
            boolean isOnMap = markerIsOnMap();
            longitudeLabel.setVisible(isOnMap);
            latitudeLabel.setVisible(isOnMap);
        });

        kantonProperty().addListener((observable, oldValue, newValue) -> {

            try {
                kantonLabel.setText(Kantone.valueOf(newValue.toUpperCase()).name);
                coatOfArms.setImage(Kantone.valueOf(newValue.toUpperCase()).coatOfArms);
                colorKanton(Kantone.valueOf(newValue.toUpperCase()));
            } catch (Exception e) {
                kantonLabel.setText("");
                coatOfArms.setImage(null);
                uncolorMap();
            }
        });

        locationMarkerX.addListener((observable, oldValue, newValue) -> {
            transition.stop();
            transition.setToX(newValue.doubleValue());
            transition.play();
        });

        locationMarkerY.addListener((observable, oldValue, newValue) -> {
            transition.stop();
            transition.setToY(newValue.doubleValue());
            transition.play();
        });

        locationMarker.translateXProperty().addListener(observable -> handleMarkerPosition());
        locationMarker.translateYProperty().addListener(observable -> handleMarkerPosition());
    }

    private void setupBindings() {

    }


    //resize by scaling
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            relocateCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateCentered() {
        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
    }

    // some handy functions

    private static SVGPath initSvgPath(String kanton){
        SVGPath init = new SVGPath();
        init.setContent(SVG_KANTONE.getString(kanton));
        init.getStyleClass().add(KANTON_STYLECLASS);
        return init;
    }

    private boolean markerIsOnKanton(Kantone kanton){
        Point2D marker = getMarkerPositionOnMap();
        return kanton.svgPath.contains(marker);
    }

    private boolean markerIsOnMap(){
        boolean isOnMap = false;

        for(Kantone k : Kantone.values())
            if(markerIsOnKanton(k))
                isOnMap = true;

        return isOnMap;
    }

    private boolean pointIsOnMap(Point2D pos){
        boolean isOnMap = false;

        for(Kantone k : Kantone.values())
            if(k.svgPath.contains(pos))
                isOnMap = true;

        return isOnMap;
    }

    private Kantone getKantonFromPoint(Point2D point){
        if(!pointIsOnMap(point))
            return null;

        for (Kantone k : Kantone.values()) {
            if (k.svgPath.contains(point)) {
                return k;
            }
        }

        return null;
    }

    private void handleMarkerPosition(){
        boolean isOnMap = false;
        for (Kantone k : Kantone.values()) {
            if (markerIsOnKanton(k)){
                isOnMap = true;
                if(clicked || getLatitudeFocus() || getLongitudeFocus()){
                    setKanton(k.toString());
                    colorKanton(k);
                }
            }
        }
        locationMarker.setVisible(isOnMap);

        if(!markerIsOnMap()){
            if(clicked || getLatitudeFocus() || getLongitudeFocus()){
                if(!handleBorderDeviation(true)){
                    uncolorMap();
                    setKanton("");
                }
            }
            handleBorderDeviation(false);
        }
        clicked = false;
    }

    private Point2D getMarkerPositionOnMap(){
        return map.parentToLocal(getLocationMarkerX()+locationMarker.getFitWidth()/2, getLocationMarkerY()+locationMarker.getFitHeight());
    }

    private void colorKanton(Kantone kanton){
        uncolorMap();
        kanton.svgPath.pseudoClassStateChanged(CHOSEN_CLASS, true);
    }

    private void uncolorMap(){
        for (Kantone k : Kantone.values()){
            k.svgPath.pseudoClassStateChanged(CHOSEN_CLASS, false);
        }
    }

    private boolean handleBorderDeviation(boolean setKanton){

        double delta = 1.2;

        Point2D markerPos = getMarkerPositionOnMap();

        Point2D rightOffset = markerPos.add(delta, 0);
        Point2D leftOffset = markerPos.subtract(delta, 0);
        Point2D topOffset = markerPos.subtract(0, delta);
        Point2D bottomOffset = markerPos.add(0, delta);

        // 0 = Right, 1 = Left, 2 = Top, 3 = Bottom
        double[] distances = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};

        if (pointIsOnMap(rightOffset)) {
            distances[0] = calculateActualDistance(markerPos, rightOffset);
        }
        if (pointIsOnMap(leftOffset)) {
            distances[1] = calculateActualDistance(markerPos, leftOffset);
        }
        if (pointIsOnMap(topOffset)) {
            distances[2] = calculateActualDistance(markerPos, topOffset);
        }
        if (pointIsOnMap(bottomOffset)) {
            distances[3] = calculateActualDistance(markerPos, bottomOffset);
        }

        double min = Arrays.stream(distances).min().getAsDouble();

        if (min < 100){

            int index = -1;
            for(int i=0; i<distances.length; i++){
                if (distances[i]==min){
                    index = i;
                    break;
                }
            }

            if(setKanton){
                switch (index){
                    case 0:
                        setKanton(getKantonFromPoint(rightOffset).toString());
                        break;
                    case 1:
                        setKanton(getKantonFromPoint(leftOffset).toString());
                        break;
                    case 2:
                        setKanton(getKantonFromPoint(topOffset).toString());
                        break;
                    case 3:
                        setKanton(getKantonFromPoint(bottomOffset).toString());
                        break;
                }
            }

            locationMarker.setVisible(true);
            longitudeLabel.setVisible(true);
            latitudeLabel.setVisible(true);

            return true;
        }
        return false;
    }

    private double calculateActualDistance(Point2D markerPos, Point2D pointOnMap){
        double delta = markerPos.distance(pointOnMap) / 10;

        double deltaX = pointOnMap.getX() - markerPos.getX();
        double deltaY = pointOnMap.getY() - markerPos.getY();

        Point2D newPoint = pointOnMap;
        Point2D oldPoint = pointOnMap;

        int i = 0;
        while(pointIsOnMap(newPoint) && i <= 10){
            oldPoint = pointOnMap;
            if (deltaX > 0){
                newPoint = newPoint.subtract(delta, 0);
            }else if (deltaX < 0){
                newPoint = newPoint.add(delta, 0);
            }else if (deltaY > 0){
                newPoint = newPoint.subtract(0, delta);
            }else if (deltaY < 0){
                newPoint = newPoint.add(0, delta);
            }
            i++;
        }

        if(i >= 10){
            return markerPos.distance(pointOnMap);
        }else{
            return markerPos.distance(oldPoint);
        }
    }

    private Label createRightAlignedLabel(double xTopRight, double yTopRight, String styleClass) {
        Label label = new Label();
        label.getStyleClass().add(styleClass);
        label.setLayoutX(xTopRight);
        label.setLayoutY(yTopRight);
        label.setAlignment(Pos.TOP_RIGHT);
        label.widthProperty().addListener((observable, oldValue, newValue) -> label.setLayoutX(xTopRight-newValue.doubleValue()));
        return label;
    }

    private String doubleToSexagesimal(double value, boolean isLongitude){

        final String DEGREE_CHAR = "\u00B0";
        final String MINUTE_CHAR = "\u2032";
        final String SECOND_CHAR = "\u2033";

        boolean isNegative = value < 0;
        value = Math.abs(value);

        int degValue = (int) value;

        value = (value-degValue) * 60;

        int minValue = (int) value;

        double secValue = (value-minValue) * 60;

        return degValue + DEGREE_CHAR + String.format("%02d", minValue) + MINUTE_CHAR + String.format("%04.1f", secValue) + SECOND_CHAR + (isLongitude ? (isNegative ? "W" : "E" ) : (isNegative ? "S" : "N" ));
    }

    // compute sizes
    @Override
    protected double computeMinWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    // alle getter und setter  (generiert via "Code -> Generate... -> Getter and Setter)

    public String getKanton() {
        return kanton.get();
    }

    public StringProperty kantonProperty() {
        return kanton;
    }

    public void setKanton(String kanton) {
        this.kanton.set(kanton);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public DoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public DoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public boolean getLongitudeFocus() {
        return longitudeFocus.get();
    }

    public BooleanProperty longitudeFocusProperty() {
        return longitudeFocus;
    }

    public void setLongitudeFocus(boolean longitudeFocus) {
        this.longitudeFocus.set(longitudeFocus);
    }

    public boolean getLatitudeFocus() {
        return latitudeFocus.get();
    }

    public BooleanProperty latitudeFocusProperty() {
        return latitudeFocus;
    }

    public void setLatitudeFocus(boolean latitudeFocus) {
        this.latitudeFocus.set(latitudeFocus);
    }

    private double getLocationMarkerX() {
        return locationMarkerX.get();
    }

    private DoubleProperty locationMarkerXProperty() {
        return locationMarkerX;
    }

    private void setLocationMarkerX(double locationMarkerX) {
        this.locationMarkerX.set(locationMarkerX);
    }

    private double getLocationMarkerY() {
        return locationMarkerY.get();
    }

    private DoubleProperty locationMarkerYProperty() {
        return locationMarkerY;
    }

    private void setLocationMarkerY(double locationMarkerY) {
        this.locationMarkerY.set(locationMarkerY);
    }

    //Kantone data
    private enum Kantone {
        AG("Aargau", "Wappen_AG.png"),
        AI("Appenzell-Innerrhoden", "Wappen_AI.png"),
        AR("Appenzell-Ausserrhoden", "Wappen_AR.png"),
        BE("Bern", "Wappen_BE.png"),
        BL("Basel-Landschaft", "Wappen_BL.png"),
        BS("Basel-Stadt", "Wappen_BS.png"),
        FR("Freiburg", "Wappen_FR.png"),
        GE("Genf", "Wappen_GE.png"),
        GL("Glarus", "Wappen_GL.png"),
        GR("Graubünden", "Wappen_GR.png"),
        JU("Jura", "Wappen_JU.png"),
        LU("Luzern", "Wappen_LU.png"),
        NE("Neuenburg", "Wappen_NE.png"),
        NW("Nidwalden", "Wappen_NW.png"),
        OW("Obwalden", "Wappen_OW.png"),
        SG("St. Gallen", "Wappen_SG.png"),
        SH("Schaffhausen", "Wappen_SH.png"),
        SO("Solothurn", "Wappen_SO.png"),
        SZ("Schwyz", "Wappen_SZ.png"),
        TG("Thurgau", "Wappen_TG.png"),
        TI("Tessin", "Wappen_TI.png"),
        UR("Uri", "Wappen_UR.png"),
        VD("Waadt", "Wappen_VD.png"),
        VS("Wallis", "Wappen_VS.png"),
        ZG("Zug", "Wappen_ZG.png"),
        ZH("Zürich", "Wappen_ZH.png");

        public final String name;
        public final Image coatOfArms;
        public final SVGPath svgPath;

        Kantone(final String name, final String file) {
            this.name = name;
            String url = getClass().getResource(COAT_OF_ARMS_FOLDER_PATH + file).toExternalForm();
            this.coatOfArms = new Image(url);
            this.svgPath = initSvgPath(this.toString());
        }
    }

}
