/**
 * <p> Group Interface Class </p>
 * 
 * <p> Description: The Group Interface serves as the main menu for Group related tasks.
 * 	   				   	   
 */

package src.CSE360App.GUI.Group_GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.CSE360App.GroupAccess;
import src.CSE360App.GUI.ArticleInterface;
import javafx.scene.control.Button;

public class GroupInterface {

	// Buttons
	private Button returnButton = new Button("Return");
	private Button createSpecialGroupButton = new Button("Create Special Group");
	private Button createGeneralGroupButton = new Button("Create General Group");
	private Button editGroup = new Button("Edit Group");
	private Button backupGroups = new Button("Backup Groups");
	private Button removeGroup = new Button("Remove Group");
	private Button restoreGroups = new Button("Restore Groups");
	
	private int width = 1000;
	private int height = 800;

	public GroupInterface(Stage primaryStage, String role) {
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Scene signUpScene = new Scene(layout, width * .5, height * .5);
		primaryStage.setScene(signUpScene);

		returnButton.setOnAction(e -> {
			ArticleInterface articleInterface = new ArticleInterface(primaryStage, role);
		});

		createSpecialGroupButton.setOnAction(e -> {
			CreateGroup createGroup = new CreateGroup(primaryStage, role, GroupAccess.SPECIAL, null);
		});
		
		createGeneralGroupButton.setOnAction(e -> {
			CreateGroup createGroup = new CreateGroup(primaryStage, role, GroupAccess.GENERAL, null);
		});
		
		editGroup.setOnAction(e -> {
		    new GroupList(primaryStage, role, "edit");
		});

		removeGroup.setOnAction(e -> {
		    new GroupList(primaryStage, role, "remove");
		});
		
		backupGroups.setOnAction(e -> {
		    new GroupList(primaryStage, role, "backup");
		});
		
		restoreGroups.setOnAction(e -> {
		    new restoreGroups(primaryStage, role);
		});
		

		layout.getChildren().addAll(returnButton, createSpecialGroupButton, createGeneralGroupButton, editGroup, backupGroups, restoreGroups, removeGroup);

	}
}
