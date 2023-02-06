package main;

import gui.RegistreerEnSelecteerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
			RegistreerEnSelecteerController parent = new RegistreerEnSelecteerController();
			Scene scene = new Scene(parent);
			
			primaryStage.setTitle("Zatre");
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
