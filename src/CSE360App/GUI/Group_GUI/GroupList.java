/**
 * <p> Group List Class </p>
 * 
 * <p> Description: The Group List allows the selection of a single article for editing of removal.
 * 	   				   	   
 */


package src.CSE360App.GUI.Group_GUI;

import java.util.ArrayList;

import javax.swing.GroupLayout.Group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
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
	private Button deleteGroup = new Button("Delete Group");
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
		groupView.setPrefWidth(width * .5);
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
