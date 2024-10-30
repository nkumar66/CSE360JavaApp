package src.CSE360App.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructorInterface {

	private Button logoutButton = new Button("Logout");

	public InstructorInterface(Stage primaryStage) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Scene signUpScene = new Scene(layout, 1000, 800);
		primaryStage.setScene(signUpScene);
		primaryStage.setTitle("Instructor Main Page");

		logoutButton.setLayoutX(900);
		logoutButton.setLayoutY(250);

		logoutButton.setOnAction(e -> {
			new LoginInterface(primaryStage);
		});
		Label welcomeLabel = new Label("Welcome! You are logged in!");
		layout.getChildren().addAll(welcomeLabel, logoutButton);
	}

}
