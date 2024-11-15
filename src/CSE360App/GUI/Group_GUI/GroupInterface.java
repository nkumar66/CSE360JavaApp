package src.CSE360App.GUI.Group_GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.CSE360App.GUI.ArticleInterface;
import javafx.scene.control.Button;

public class GroupInterface {

	private Button returnButton = new Button("Return");
	private Button createGroupButton = new Button("Create Group");
	private Button editGroup = new Button("Edit Group");
	private Button removeGroup = new Button("Remove Group");

	public GroupInterface(Stage primaryStage, String role) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Scene signUpScene = new Scene(layout, 1000, 800);
		primaryStage.setScene(signUpScene);

		returnButton.setOnAction(e -> {
			ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
		});

		createGroupButton.setOnAction(e -> {
			CreateGroup createGroup = new CreateGroup(primaryStage, role);
		});

		layout.getChildren().addAll(returnButton, createGroupButton, editGroup, removeGroup);

	}
}
