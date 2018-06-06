package ch.fhnw.oop2.hydropowerfx.custom_controls.WaterQuantity;

import java.util.List;
import java.util.Locale;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * Dieses CustomControl ist ein reines Anzeigeelement. Es zeigt die Wasserdurchflussmenge an.
 * Je mehr Wasser, desto höher der Wasserstand. usätzlich kannie prozentuale Wassermenge kann mit dem Slider angepasst werden.
 *
 * @author Dieter Holz, Alessandro Calcagno, Valentina Giampa
 */
public class WaterQuantityControl extends Region {
    // needed for StyleableProperties
    private static final StyleablePropertyFactory<WaterQuantityControl> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    private static final Locale CH = new Locale("de", "CH");

    private static final double ARTBOARD_WIDTH  = 200;  // DONE: Breite der "Zeichnung" aus dem Grafik-Tool übernehmen
    private static final double ARTBOARD_HEIGHT = 200;  // DONE: Anpassen an die Breite der Zeichnung

    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;

    private static final double MINIMUM_WIDTH  = 32;    // DONE: Anpassen
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;


    private static final double MAXIMUM_WIDTH = 32;    // DONE: Anpassen

    // DONE: diese Parts durch alle notwendigen Parts der gewünschten CustomControl ersetzen
    private Text   display;

    private Pane pane;

    private Circle backgroundCircle;

    private Rectangle airRectangle;

    private SVGPath lightningSVG;

    // Minimum quantity level a power plant can have
    private double minWaterValue;

    // Maximum quantity level a power plant can have
    private double maxWaterValue;



    // DONE: ersetzen durch alle notwendigen Properties der CustomControl
    private final DoubleProperty value = new SimpleDoubleProperty();

    // DONE: ergänzen mit allen  CSS stylable properties
    private static final CssMetaData<WaterQuantityControl, Color> BASE_COLOR_META_DATA = FACTORY.createColorCssMetaData("-base-color", s -> s.baseColor);

    private final StyleableObjectProperty<Color> baseColor = new SimpleStyleableObjectProperty<Color>(BASE_COLOR_META_DATA, this, "baseColor") {
        @Override
        protected void invalidated() {
            setStyle(getCssMetaData().getProperty() + ": " + colorToCss(get()) + ";");
            applyCss();
        }
    };

    // needed for resizing
    private Pane drawingPane;

    public WaterQuantityControl(double minWaterValue, double maxWaterValue) {
        initializeRanges(minWaterValue, maxWaterValue);
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeRanges(double minWaterValue, double maxWaterValue) {
        this.minWaterValue = minWaterValue;
        this.maxWaterValue = maxWaterValue;
    }

    private void initializeSelf() {
        // load stylesheets
        String fonts = getClass().getResource("fonts/fonts.css").toExternalForm();
        getStylesheets().add(fonts);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("waterquantity-control");  // DONE: an den Namen der Klasse (des CustomControls) anpassen
    }

    private void initializeParts() {
        //DONE: alle deklarierten Parts initialisieren
        double center = ARTBOARD_WIDTH * 0.5;
        center = center - 1;

        backgroundCircle = new Circle(center, center, center);
        backgroundCircle.setStrokeWidth(0);
        backgroundCircle.getStyleClass().add("background-circle");

        Circle backgroundClipCircle = new Circle(center, center, center - 1);
        backgroundClipCircle.getStyleClass().add("background-clip-circle");

        airRectangle = new Rectangle(0, 0, center * 2, center);
        airRectangle.setFill(Color.rgb(41, 37, 29));

        airRectangle.setClip(backgroundClipCircle);
        airRectangle.getStyleClass().add("air-rectangle");
        airRectangle.setEffect(new Glow(90));

        lightningSVG = new SVGPath();
        lightningSVG.getStyleClass().add("lightning");
        lightningSVG.setContent("M80.6663839,79.5 L46.9418983,79.5 C46.9212241,79.5 46.9007454,79.4991635 46.880494,79.4975225 C46.7716615,79.5168008 46.6568672,79.5122194 46.5434654,79.479702 C46.1452978,79.3655293 45.9150744,78.9501954 46.0292472,78.5520277 L63.1876726,18.7134872 C63.2257788,18.5805948 63.2974331,18.4664108 63.3908002,18.3774953 C63.5201198,18.1519444 63.7632659,18 64.0419006,18 L133.941898,18 C134.199967,18 134.427593,18.1303422 134.562535,18.3287862 C134.803025,18.5102989 134.919683,18.8274516 134.831667,19.1344023 L123.113645,60 L148.791895,60 C149.206108,60 149.541895,60.3357864 149.541895,60.75 C149.541895,60.8035828 149.536276,60.8558532 149.525595,60.9062541 C149.554067,61.0270554 149.552898,61.1566118 149.516255,61.2844 L132.679828,120 L152.98613,120 C153.231875,119.928071 153.508177,119.983903 153.709026,120.171198 C154.011963,120.453691 154.028536,120.928275 153.746043,121.231212 L97.3543118,181.70394 C97.312571,181.85621 97.2225963,181.996596 97.0876757,182.100125 C96.7590579,182.352282 96.2882468,182.290298 96.0360896,181.961681 L49.6484694,121.508157 C49.6241348,121.476444 49.602726,121.443406 49.5842001,121.409368 C49.3505177,121.282331 49.1918983,121.034689 49.1918983,120.75 C49.1918983,120.335786 49.5276847,120 49.9418983,120 L69.0531958,120 L80.6663839,79.5 Z");
        lightningSVG.setFill(Color.rgb(225, 137, 39));
        lightningSVG.setOpacity(90);
        lightningSVG.setEffect(new Glow(44));


      //  display = createCenteredText("display");
        display = createCenteredText(0, 0, "display");
        display.setUnderline(false);

        display.setX(ARTBOARD_WIDTH);
        display.setY(ARTBOARD_HEIGHT);
        display.setFill(Color.rgb(82, 74, 58));

        // Trigger an initial input
        valueProperty().setValue(3);
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);

    }

    private void layoutParts() {
        // DONE: alle Parts zur drawingPane hinzufügen
        drawingPane.getChildren().addAll(backgroundCircle, airRectangle, lightningSVG );

        setMaxHeight(32);
        setMaxWidth(54);

        getChildren().addAll(drawingPane);
        setPadding(new Insets(0, 0, 30, 0));
    }

    private void setupEventHandlers() {
        //no operations
    }

    private void setupValueChangeListeners() {
        valueProperty().addListener((observable, oldValueNumber, newValueNumber) -> {
            double newValue = newValueNumber.doubleValue();

            // Check the minimum range
            if(newValue < minWaterValue) {
                System.err.println("Water quantity control received a value that is subsiding the minimum value of " + minWaterValue + " (Value will be reset to minimum value!): " + newValue);
                newValue = minWaterValue;
            }

            // Check the maximum range
            if(newValue > maxWaterValue) {
                System.err.println("Water quantity control received a value that is exceeding the maximum value of " + maxWaterValue + " (Value will be reset to maximum value!): " + newValue);
                newValue = maxWaterValue;
            }

            // Calculate the ratio (Range from 0.0 to 1.0 like percentage/100)
            double valueRatio = (newValue - minWaterValue) / (maxWaterValue - minWaterValue);

            // Get the whole height and substract this value
            airRectangle.setHeight(ARTBOARD_HEIGHT - (ARTBOARD_HEIGHT * valueRatio));

            // Update the text manually (This ensures proper range handling)
            display.setText(String.format("%.2f", newValue));
        });
    }

    private void setupBindings() {
        //no operations
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
            drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            //relocateCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    // some handy functions

    //DONE: diese Funktionen anschauen und für die Umsetzung des CustomControls benutzen


 /*   private Text createCenteredText(String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);

        return createCenteredText(ARTBOARD_WIDTH * 0.5, ARTBOARD_HEIGHT * 0.5, styleClass);

    }
*/
    private Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.LEFT);
        double width = cx > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        double height = cy > ARTBOARD_WIDTH * 0.5 ? ((ARTBOARD_WIDTH - cx) * 2.0) : cx * 2.0;
        text.setY(cy);
        text.setX(cx - (width / 2.0));    //2.0

        return text;
    }

    private String colorToCss(final Color color) {
  		return color.toString().replace("0x", "#");
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

    // DONE: ersetzen durch die Getter und Setter Ihres CustomControls
    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public Color getBaseColor() {
        return baseColor.get();
    }

    public StyleableObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor.set(baseColor);
    }
    public Text getDisplay() {
        return display;
    }

    public void setDisplay(Text display) {
        this.display = display;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
    public Circle getBackgroundCircle() {
        return backgroundCircle;
    }

    public void setBackgroundCircle(Circle backgroundCircle) {
        this.backgroundCircle = backgroundCircle;
    }

    public Rectangle getAirRectangle() {
        return airRectangle;
    }

    public void setAirRectangle(Rectangle airRectangle) {
        this.airRectangle = airRectangle;
    }

    public SVGPath getLightningSVG() {
        return lightningSVG;
    }

    public void setLightningSVG(SVGPath lightningSVG) {
        this.lightningSVG = lightningSVG;
    }

    public void setLightningFillColor(Color color){
        lightningSVG.setFill(color);
    }


}
