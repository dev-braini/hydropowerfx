package ch.fhnw.oop2.hydropowerfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class HydroPowerApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		RootPM rootPM    = new RootPM();
		Parent rootPanel = new RootPanel(rootPM);

		Scene scene = new Scene(rootPanel);

		primaryStage.titleProperty().bind(rootPM.applicationTitleProperty());
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(1024);
		primaryStage.setMinHeight(768);

		Platform.setImplicitExit(false);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				if(rootPM.getButtonNavControlSaveActive()) {
					alert.setTitle("Close Confirmation Dialog");
					alert.setHeaderText("Sie haben noch ungespeicherte Änderungen!");

					ButtonType buttonDiscard = new ButtonType("Ohne speichern verlassen");
					ButtonType buttonSave = new ButtonType("Speichern und verlassen");
					ButtonType buttonCancel = new ButtonType("Abbrechen");

					alert.getButtonTypes().setAll(buttonDiscard, buttonSave, buttonCancel);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonDiscard) Platform.exit();
					if (result.get() == buttonSave) {
						rootPM.savePowerStationList();
						Platform.exit();
					}
					if (result.get() == buttonCancel) we.consume();
				} else Platform.exit();
			}
	   	});


		primaryStage.getIcons().add(new Image("file:///" + System.getProperty("user.dir") + "/src/main/resources/icons/logo.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
