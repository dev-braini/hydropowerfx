package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

/*
 * @author: Marco Peter & Markus Winter
 */
public class TimeView extends VBox implements ViewMixin {
    private final RootPM rootPM;
    private ScrollPane   scrollPane;
    private VBox         timeView;


    private static void ensureVisible(ScrollPane pane, Node node) {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();

        double x = node.getBoundsInParent().getMaxX();
        double y = node.getBoundsInParent().getMaxY();

        Timeline timeline = new Timeline();

        timeline.setCycleCount(1);
        KeyValue kv = new KeyValue(pane.vvalueProperty(), (y/height), Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);

        timeline.getKeyFrames().add(kf);
        timeline.play();

    }

    public TimeView(RootPM model) {
        this.rootPM = model;

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


        final EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ensureVisible(scrollPane, timeView.getChildren().get(timeView.getChildren().size()-1));
            }
        };


        final EventHandler<ActionEvent> handler2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ensureVisible(scrollPane, timeView.getChildren().get(0));
            }
        };

        Button btn = new Button(" bottom ");
        Button btn2 = new Button(" top ");
        btn.setOnAction(handler);
        btn2.setOnAction(handler2);

        timeView.getChildren().add(btn);

        for (int i = 0; i < 20; i++) {
            Image image                   = new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/images/timeView/timeView-el-"+i%2+".png");
            ImageView imageView           = new ImageView(image);
            Label labelYear = new Label("1945");
            Label labelName = new Label("Laufenburg " + i);

            labelYear.getStyleClass().add("timeView-label-year-"+i%2);
            labelName.getStyleClass().add("timeView-label-name-"+i%2);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imageView, labelYear, labelName);

            timeView.getChildren().addAll(stackPane);
        }

        timeView.getChildren().add(btn2);

        scrollPane.setContent(timeView);
        this.getChildren().add(scrollPane);

    }

    @Override
    public void setupBindings() {

    }
}
