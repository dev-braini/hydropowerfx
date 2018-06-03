package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import java.util.Locale;

/*
 * @author: Markus Winter
 */
public class SpecViewTime extends VBox implements ViewMixin {
    private final RootPM      rootPM;
    private ScrollPane        scrollPane;
    private VBox              timeView;
    private TableView         powerStationTable;


    private static void ensureVisible(ScrollPane pane, Node node) {
        double   height   = pane.getContent().getBoundsInLocal().getHeight();
        double   y        = node.getBoundsInParent().getMaxY();
        double   duration = Math.abs((y/height) - pane.getVvalue())+1;
        Timeline timeline = new Timeline();

        duration = duration*duration*700;
        timeline.setCycleCount(1);

        KeyValue kv = new KeyValue(pane.vvalueProperty(), (y/height), Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.millis(duration), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public SpecViewTime(RootPM model, TableView powerStationTable) {
        this.rootPM = model;
        this.powerStationTable = powerStationTable;
        this.setVisible(false);

        init();
    }

    @Override
    public void initializeSelf() {
        this.setId("main-content-right-time");
        this.addStylesheetFiles("../css/TimeView.css");
    }

    @Override
    public void initializeControls() {
        scrollPane = new ScrollPane();
        timeView   = new VBox();
    }

    @Override
    public void layoutControls() {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);


        /*final EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ensureVisible(scrollPane, timeView.getChildren().get(timeView.getChildren().size()-1));
            }
        };*/

        int i = 0;

        for (PowerStation powerStation : rootPM.getPowerStationListSortedFirstCommissioning()){
            Image image                   = new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/images/timeView/timeView-el-"+i%2+".png");
            ImageView imageView           = new ImageView(image);
            Label labelYear               = new Label();
            Label labelName               = new Label();

            labelYear.getStyleClass().add("timeView-label-year-"+i%2);
            labelName.getStyleClass().add("timeView-label-name-"+i%2);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imageView, labelYear, labelName);

            labelYear.textProperty().bindBidirectional( powerStation.firstCommissioningProperty(), new NumberStringConverter(Locale.GERMAN, "#####") );
            labelName.textProperty().bindBidirectional( powerStation.nameProperty());

            timeView.getChildren().add(stackPane);

            i++;
        }

        scrollPane.setContent(timeView);
        this.getChildren().add(scrollPane);
    }

    @Override
    public void setupValueChangedListeners() {
        powerStationTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PowerStation selectedEL = (PowerStation) powerStationTable.getSelectionModel().getSelectedItem();
                Integer selectedIndex = powerStationTable.getSelectionModel().getSelectedIndex();

                ensureVisible(scrollPane, timeView.getChildren().get(
                        rootPM.getPowerStationIndex(selectedEL)
                ));

                //.getChildren().get(selectedIndex).getStyleClass().add("timeview-active");

            }
        });
    }

    @Override
    public void setupBindings() {

    }
}
