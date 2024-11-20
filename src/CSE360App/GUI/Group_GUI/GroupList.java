/**
 * <p> Group List Class </p>
 * 
 * <p> Description: The Group List allows the selection of a single article for editing of removal.
 * 	   				   	   
 */


package src.CSE360App.GUI.Group_GUI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.GroupLayout.Group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import src.CSE360App.Article;
import src.CSE360App.GroupManager;
import src.CSE360App.StudentClass;
import src.CSE360App.UserClass;
import src.CSE360App.UserGroups;

public class GroupList {

	// Lists for Selection
	private ArrayList<UserGroups> groupsList = GroupManager.getGroups();
	private ObservableList<UserGroups> selectable_groupsList = FXCollections.observableArrayList();
	private ListView<UserGroups> groupView = new ListView<>(selectable_groupsList);

	// Buttons
	private Button editGroup = new Button("Edit Group");
	private Button browseButton = new Button("Browse");
	private Button deleteGroup = new Button("Delete Group");
	private Button backupGroups = new Button("Backup Groups");
	private Button returnButton = new Button("Return");

	private int width = 1000;
	private int height = 800;

	/***
	 * GroupList: Presents a selectable lists w/ all groups visible to that user.
	 * 
	 * @param primaryStage
	 * @param role
	 * @param action
	 */
	public GroupList(Stage primaryStage, String role, String action) {


		// Populate selectable_groupsList
		
		selectable_groupsList.addAll(groupsList);
		HBox groupViewContainer = new HBox();
		groupViewContainer.setAlignment(Pos.CENTER);
		groupViewContainer.getChildren().add(groupView);

		// Set preferred and maximum width for the groupView
		groupView.setPrefWidth(width * 0.5);
		groupView.setMaxWidth(width * 0.5); 
		UserGroups.setColumnAttributes(groupView);

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

		Scene groupListScene = new Scene(layout, width, height);
		primaryStage.setScene(groupListScene);

		layout.getChildren().add(groupView);

		// Add relevant button based on action
		if (action.equalsIgnoreCase("edit")) {
			layout.getChildren().add(editGroup);

			editGroup.setOnAction(e -> {
				UserGroups groupSelected = groupView.getSelectionModel().getSelectedItem();
				if (groupSelected != null) {
					// Open CreateGroup for editing
					new CreateGroup(primaryStage, role, groupSelected.getAccessLevel(), groupSelected);
				}
			});

		} else if (action.equalsIgnoreCase("remove")) {
			layout.getChildren().add(deleteGroup);

			deleteGroup.setOnAction(e -> {
				UserGroups groupSelected = groupView.getSelectionModel().getSelectedItem();
				if (groupSelected != null) {
					// Remove group logic
					deleteGroup(groupSelected);
				}
			});
		} else if(action.equalsIgnoreCase("backup")) {
			TextField filePath = new TextField();
			filePath.setPromptText("Click 'Browse' to select the backup file.");
			filePath.setPrefWidth(.3 * width);

			browseButton.setOnAction(e -> {
				DirectoryChooser fileChooser = new DirectoryChooser();
				fileChooser.setTitle("Select a Directory");

				File selectedDirectory = fileChooser.showDialog(primaryStage);

				if (selectedDirectory != null) {
					filePath.setText(selectedDirectory.getAbsolutePath());
				}
			});
			
			backupGroups.setOnAction(e -> {
				// ADD BACKUP LOGIC HERE!

				System.out.println("you restored!");
			});
			HBox fileSelection = new HBox(10, filePath, browseButton, backupGroups);
			fileSelection.setAlignment(Pos.CENTER);
			layout.getChildren().add(fileSelection);
			layout.setAlignment(Pos.CENTER);

		}

		// Return to previous menu
		returnButton.setOnAction(e -> {
			new GroupInterface(primaryStage, role);
		});

		layout.getChildren().add(returnButton);
	}

	// Removes group from group list.
	private void deleteGroup(UserGroups group) {
		groupsList.remove(group); // Remove from underlying list
		selectable_groupsList.remove(group); // Remove from displayed list
		System.out.println("Deleted group: " + group.getGroupName());
	}
}
