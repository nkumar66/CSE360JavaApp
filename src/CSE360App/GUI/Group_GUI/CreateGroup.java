/**
 * <p>
 * createGroup Class
 * </p>
 * 
 * <p>
 * Description: The GUI Component of creating a groupo.
 * 
 */

package src.CSE360App.GUI.Group_GUI;

/***
 * Necessary imports for this class.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.CSE360App.AdminClass;
import src.CSE360App.Article;
import src.CSE360App.UserGroups;
import src.CSE360App.GroupAccess;
import src.CSE360App.GroupManager;
import src.CSE360App.StudentClass;
import src.CSE360App.UserClass;
import src.CSE360App.GUI.ArticleInterface;

public class CreateGroup {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	// These are the application values required by the Graphical User Interface
	// The names of the variables specify their function and each is initialize as
	// required

	/***
	 * **IMPORTANT NOTE: WHEN INTEGRATING DATABASE, JUST FILL THESE ARRAY LIST WITH
	 * ALL OF DATABASE DATA, THEN WHEN USERS CREATES GROUP, JUST JUST FINAL ARRAY
	 * LISTS TO CREATE GROUP OBJECT ***
	 */

	// Temp: Makes fake admins, instructors users and articles for the sake of
	// testing GUI
	private ArrayList<Article> articles = new ArrayList<>();
	private ObservableList<Article> articleList = FXCollections.observableArrayList();

	private ArrayList<StudentClass> students = new ArrayList<>();
	private ObservableList<StudentClass> studentList = FXCollections.observableArrayList();

	private ArrayList<UserClass> instructors = new ArrayList<>();
	private ObservableList<UserClass> instructorList = FXCollections.observableArrayList();
	private ArrayList<AdminClass> admins = new ArrayList<>();

	private ObservableList<AdminClass> adminList = FXCollections.observableArrayList();

	private ArrayList<Article> selected_Articles = new ArrayList<>();
	private ObservableList<Article> selected_ArticlesList = FXCollections.observableArrayList();

	private ArrayList<StudentClass> selected_Students = new ArrayList<>();
	private ObservableList<StudentClass> selected_studentList = FXCollections.observableArrayList();

	private ArrayList<UserClass> selected_Instructors = new ArrayList<>();
	private ObservableList<UserClass> selected_InstructorList = FXCollections.observableArrayList();

	private ArrayList<AdminClass> selected_Admins = new ArrayList<>();
	private ObservableList<AdminClass> selected_AdminsList = FXCollections.observableArrayList();

	// Maps / ArrayList that hold the final version of each list with permissions
	// that will be stored in database
	private Map<StudentClass, ArrayList<String>> final_groupStudents = new HashMap<>();
	private Map<UserClass, ArrayList<String>> final_groupInstructors = new HashMap<>();
	private Map<AdminClass, ArrayList<String>> final_groupAdmins = new HashMap<>();
	private ArrayList<Article> final_groupArticles = new ArrayList<>();

	// Buttons
	private Button returnButton = new Button("Return");
	private Button createGroup = new Button("Create Group");
	private Button addStudent = new Button("Add Selected Student");
	private Button addInstructor = new Button("Add Selected Instructor");
	private Button addAdmin = new Button("Add Selected Admin");
	private Button addArticle = new Button("Add Selected Article");

	private Button editAdmin = new Button("Edit Selected Admin");
	private Button removeAdmin = new Button("Remove Selected Admin");

	private Button editInstructor = new Button("Edit Selected Instructor");
	private Button removeInstructor = new Button("Remove Selected Instructor");

	private Button editStudent = new Button("Edit Selected Student");
	private Button removeStudent = new Button("Remove Selected Student");

	private Button removeArticle = new Button("Remove Selected Admin");

	// Labels / Textfields
	private Label label_SelectUser = new Label("Select Users");
	private Label label_SelectInstructors = new Label("Select Instructors");
	private Label label_SelectAdmins = new Label("Select Admins");
	private Label label_SelectArticles = new Label("Select Articles");
	private Label label_GroupTitle = new Label("Group Title: ");
	private Label label_selectedUsersList = new Label("Selected Users");
	private Label label_selectedInstructorsList = new Label("Selected Instructors");
	private Label label_selectedAdminsList = new Label("Selected Admins");
	private Label label_selectedArticlesList = new Label("Selected Articles");
	private TextField text_GroupTitle = new TextField();

	// Alerts
	Alert alert = new Alert(AlertType.CONFIRMATION);
	Alert confirm_FirstInstructor = new Alert(AlertType.CONFIRMATION);

	// Customizable window size for Article reader.
	private int width = 1000;
	private int height = 800;


	
/***
 * 	 * CreateGroup: Window that allows the custom creation of "Groups" where users
	 * can be given individual permissions, articles can be selected, etc.
	 * 
	 * If a group is passed in, it pre-populates the selected attributes of that group and their permissions.
	 * Otherwise, it just creates a new group.
 * @param primaryStage
 * @param role
 * @param access_level
 * @param group
 */
	
	
	public CreateGroup(Stage primaryStage, String role, GroupAccess access_level, UserGroups group ) {

		// Process that creates fake articles, and fills clickable list of them.
		Article.populateArticles(articles);
		articleList.addAll(articles);

		// Process that creates fake users, and fills clickable list of them.
		StudentClass.populateStudents(students);
		studentList.addAll(students);
		AdminClass.populateAdmins(admins);
		adminList.addAll(admins);
		UserClass.populateUsers(instructors);
		instructorList.addAll(instructors);
		


		// HBox holding all text prompts for group creation
		HBox textPrompts = new HBox(10);
		text_GroupTitle.setPromptText("Enter Group Title Here");
		textPrompts.getChildren().addAll(label_GroupTitle, text_GroupTitle);

		/***
		 * Below is the collection of clickable fields, where users can add / remove
		 * from the group they are creating
		 * 
		 * ListView objects are the selected lists. All of their attributes are being
		 * handled below.
		 */
		ListView<Article> articleView = new ListView<>(articleList);
		Article.setColumnArticleAttributes(articleView);

		ListView<StudentClass> studentView = new ListView<>(studentList);
		UserClass.setColumnAttributes(studentView);

		ListView<UserClass> instructorView = new ListView<>(instructorList);
		UserClass.setColumnAttributes(instructorView);

		ListView<AdminClass> adminView = new ListView<>(adminList);
		UserClass.setColumnAttributes(adminView);

		ListView<Article> selected_articleView = new ListView<>(selected_ArticlesList);
		Article.setColumnArticleAttributes(selected_articleView);

		ListView<StudentClass> selected_userView = new ListView<>(selected_studentList);
		UserClass.setColumnAttributes(selected_userView);

		ListView<UserClass> selected_InstructorView = new ListView<>(selected_InstructorList);
		UserClass.setColumnAttributes(selected_InstructorView);

		ListView<AdminClass> selected_AdminView = new ListView<>(selected_AdminsList);
		UserClass.setColumnAttributes(selected_AdminView);

		
		/***
		 * The code below pre-populates the selectable list if applicable for editing groups.
		 */
		if (group != null) {
		    // Prefill selected articles
		    selected_ArticlesList.addAll(group.getArticles());
//		    System.out.println("Before removal: " + articleList); // Debug
		    articleList.removeAll(group.getArticles()); // Remove selected articles from the selectable list
		    articleView.setItems(FXCollections.observableArrayList(articleList)); // Refresh UI
//		    System.out.println("After removal: " + articleList); // Debug


		    // Prefill selected students and their permissions
		    for (Map.Entry<StudentClass, ArrayList<String>> entry : group.getStudents().entrySet()) {
		        StudentClass student = entry.getKey();
		        ArrayList<String> permissions = entry.getValue();

		        selected_studentList.add(student); // Add to selected students
		        final_groupStudents.put(student, permissions); // Save their permissions
		        studentList.remove(student); // Remove from selectable students
		    }
		    studentView.setItems(FXCollections.observableArrayList(studentList)); // Refresh UI


		    // Prefill selected instructors and their permissions
		    for (Map.Entry<UserClass, ArrayList<String>> entry : group.getInstructors().entrySet()) {
		        UserClass instructor = entry.getKey();
		        ArrayList<String> permissions = entry.getValue();

		        selected_InstructorList.add(instructor); // Add to selected instructors
		        final_groupInstructors.put(instructor, permissions); // Save their permissions
		        instructorList.remove(instructor); // Remove from selectable instructors
		    }
		    instructorView.setItems(FXCollections.observableArrayList(instructorList)); // Refresh UI


		    // Prefill selected admins and their permissions
		    for (Map.Entry<AdminClass, ArrayList<String>> entry : group.getAdmins().entrySet()) {
		        AdminClass admin = entry.getKey();
		        ArrayList<String> permissions = entry.getValue();

		        selected_AdminsList.add(admin); // Add to selected admins
		        final_groupAdmins.put(admin, permissions); // Save their permissions
		        adminList.remove(admin); // Remove from selectable admins
		    }
		    adminView.setItems(FXCollections.observableArrayList(adminList)); // Refresh UI
		}

		// Sets width of selectable lists.
		articleView.setPrefWidth(width * .25);
		studentView.setPrefWidth(width * .25);
		instructorView.setPrefWidth(width * .25);
		adminView.setPrefWidth(width * .25);

		selected_articleView.setPrefWidth(width * .25);
		selected_userView.setPrefWidth(width * .25);
		selected_AdminView.setPrefWidth(width * .25);
		selected_InstructorView.setPrefWidth(width * .25);

		/***
		 * The following h/vboxes are used to organize every single selectable list. The
		 * 2 hboxes represent the top and bottom row of lists The Vboxes represent the
		 * containers that hold each list's labels, buttons, etc.
		 * 
		 */
		HBox selectListContainer = new HBox(10);
		VBox usersContainer = new VBox(10);
		VBox instructorsContainer = new VBox(10);
		VBox adminsContainer = new VBox(10);
		VBox articlesContainer = new VBox(10);

		usersContainer.getChildren().addAll(label_SelectUser, studentView, addStudent);

		instructorsContainer.getChildren().addAll(label_SelectInstructors, instructorView, addInstructor);
		adminsContainer.getChildren().addAll(label_SelectAdmins, adminView, addAdmin);
		articlesContainer.getChildren().addAll(label_SelectArticles, articleView, addArticle);
		selectListContainer.getChildren().addAll(usersContainer, instructorsContainer, adminsContainer,
				articlesContainer);

		HBox selectedListContainer = new HBox(10);
		VBox selectedusersContainer = new VBox(10);
		VBox selectedinstructorsContainer = new VBox(10);
		VBox selectedadminsContainer = new VBox(10);
		VBox selectedarticlesContainer = new VBox(10);

		selectedusersContainer.getChildren().addAll(label_selectedUsersList, selected_userView, editStudent,
				removeStudent);
		selectedinstructorsContainer.getChildren().addAll(label_selectedInstructorsList, selected_InstructorView,
				editInstructor, removeInstructor);
		selectedadminsContainer.getChildren().addAll(label_selectedAdminsList, selected_AdminView, editAdmin,
				removeAdmin);
		selectedarticlesContainer.getChildren().addAll(label_selectedArticlesList, selected_articleView, removeArticle);
		selectedListContainer.getChildren().addAll(selectedusersContainer, selectedinstructorsContainer,
				selectedadminsContainer, selectedarticlesContainer);

		/***
		 * The buttons below are used to select, modify and remove from the student
		 * list.
		 * 
		 */
		addStudent.setOnAction(e -> {
			StudentClass userToAdd = studentView.getSelectionModel().getSelectedItem();
			if (userToAdd != null && addUserDialog(userToAdd, access_level, true))
				;
			{

				studentView.getItems().remove(userToAdd);
				selected_studentList.add(userToAdd);

			}
		});

		editStudent.setOnAction(e -> {
			StudentClass userToEdit = selected_userView.getSelectionModel().getSelectedItem();
			if (userToEdit != null && addUserDialog(userToEdit, access_level, true))
				;

		});

		removeStudent.setOnAction(e -> {
			StudentClass userToRemove = selected_userView.getSelectionModel().getSelectedItem();
			if (userToRemove != null && removeItem(userToRemove, "user") ) {
				
				
				final_groupStudents.remove(userToRemove);
				
				studentView.getItems().add(userToRemove);
				selected_userView.getItems().remove(userToRemove);
			}

		});
		
		/***
		 * The buttons below are used to select, modify and remove from the instructor list.
		 * 
		 */

		/***
		 * The following button is used to add instructors to the selected list. It
		 * brings up an additonal confirmation dialog, to ensure user is okay with this
		 * user getting admin rights.
		 * 
		 * 
		 */
		addInstructor.setOnAction(e -> {
			UserClass userToAdd = instructorView.getSelectionModel().getSelectedItem();

			if (selected_InstructorList.isEmpty() && access_level == GroupAccess.SPECIAL) {
				if (userToAdd != null && confirmFirstInstructor(userToAdd)) {
					ArrayList<String> permissions = new ArrayList<>();
					permissions.add("admin_rights");
					permissions.add("read_bodies");
					instructorView.getItems().remove(userToAdd);
					selected_InstructorList.add(userToAdd);
					final_groupInstructors.put(userToAdd, permissions);
				}

			} else {
				if (userToAdd != null && addUserDialog(userToAdd, access_level, false)) {
					instructorView.getItems().remove(userToAdd);
					selected_InstructorList.add(userToAdd);

				}
			}

		});
		
		

		editInstructor.setOnAction(e -> {
			UserClass userToEdit = selected_InstructorView.getSelectionModel().getSelectedItem();
			if (userToEdit != null && addUserDialog(userToEdit, access_level, true));

		});

		removeInstructor.setOnAction(e -> {
			UserClass userToRemove = selected_InstructorView.getSelectionModel().getSelectedItem();
			if (userToRemove != null && removeItem(userToRemove, "user") )  {
				
				
				final_groupInstructors.remove(userToRemove);
				
				instructorView.getItems().add(userToRemove);
				selected_InstructorView.getItems().remove(userToRemove);
			}

		});
		
		
		

		/***
		 * The buttons below are used to select, modify and remove from the admin list.
		 * 
		 */
		addAdmin.setOnAction(e -> {
			AdminClass userToAdd = adminView.getSelectionModel().getSelectedItem();
			if (userToAdd != null && addUserDialog(userToAdd, access_level, false)) {
				adminView.getItems().remove(userToAdd);
				selected_AdminsList.add(userToAdd);

			}
		});
		

		editAdmin.setOnAction(e -> {
			AdminClass userToEdit = selected_AdminView.getSelectionModel().getSelectedItem();
			if (userToEdit != null && addUserDialog(userToEdit, access_level, true));

		});

		removeAdmin.setOnAction(e -> {
			AdminClass userToRemove = selected_AdminView.getSelectionModel().getSelectedItem();
			if (userToRemove != null && removeItem(userToRemove, "user")  ) {
				
				
				final_groupAdmins.remove(userToRemove);
				
				adminView.getItems().add(userToRemove);
				selected_AdminView.getItems().remove(userToRemove);
			}

		});

		articleView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		
		/***
		 * The buttons below are used to select and remove from the article list.
		 * 
		 */
		addArticle.setOnAction(e -> {
			 ObservableList<Article> selectedArticles = articleView.getSelectionModel().getSelectedItems();

			  // Add selected articles to the selected list and final group
			    selected_ArticlesList.addAll(selectedArticles);
			    final_groupArticles.addAll(selectedArticles);

			    // Remove selected articles from the selectable list
			    articleList.removeAll(selectedArticles);
			    articleView.setItems(FXCollections.observableArrayList(articleList)); // Refresh UI
		});
		
		removeArticle.setOnAction(e -> {
		    // Get all selected articles
		    ObservableList<Article> selectedArticlesToRemove = selected_articleView.getSelectionModel().getSelectedItems();

		    if (!selectedArticlesToRemove.isEmpty()) {
		        // Remove from final group
		        final_groupArticles.removeAll(selectedArticlesToRemove);

		        // Add back to the original list
		        articleList.addAll(selectedArticlesToRemove);

		        // Remove from the selected list
		        selected_ArticlesList.removeAll(selectedArticlesToRemove);

		        // Refresh ListViews
		        articleView.setItems(FXCollections.observableArrayList(articleList));
		        selected_articleView.setItems(FXCollections.observableArrayList(selected_ArticlesList));
		    }
		});


		/***
		 * As of now, create group button just prints values to console to prove it is
		 * collecting data successfully. NEEDS TO SEND DATA STRUCTURES TO DB
		 * 
		 */
		createGroup.setOnAction(e -> {
			System.out.printf("Title: %s\n", text_GroupTitle.getText());
			String groupName = text_GroupTitle.getText();

//			System.out.println("Final Group Students:");
//			for (Map.Entry<StudentClass, ArrayList<String>> entry : final_groupStudents.entrySet()) {
//				String username = entry.getKey().getUsername(); // Assuming getUsername() exists in StudentClass
//				ArrayList<String> values = entry.getValue();
//				System.out.println("Key (Username): " + username);
//				System.out.println("Values: " + values);
//			}
//
//			// Iterate and print final_groupInstructors
//			System.out.println("\nFinal Group Instructors:");
//			for (Map.Entry<UserClass, ArrayList<String>> entry : final_groupInstructors.entrySet()) {
//				String username = entry.getKey().getUsername(); // Assuming getUsername() exists in UserClass
//				ArrayList<String> values = entry.getValue();
//				System.out.println("Key (Username): " + username);
//				System.out.println("Values: " + values);
//			}
//
//			// Iterate and print final_groupAdmins
//			System.out.println("\nFinal Group Admins:");
//			for (Map.Entry<AdminClass, ArrayList<String>> entry : final_groupAdmins.entrySet()) {
//				String username = entry.getKey().getUsername(); // Assuming getUsername() exists in AdminClass
//				ArrayList<String> values = entry.getValue();
//				System.out.println("Key (Username): " + username);
//				System.out.println("Values: " + values);
//			}
//			System.out.println("\nFinal Group Articles:");
//			for (Article art : final_groupArticles) {
//				System.out.println(art.getTitle());
//			}

			
			// ***** PUT THIS INTO DB!!!!***
		    if (group != null) {
		    	UserGroups existingGroup = GroupManager.getGroupByID(group.getID());
		        // Update the existing group
		    	existingGroup.setGroupName(groupName);
		        existingGroup.setArticles(final_groupArticles);
		        existingGroup.setAdmins(final_groupAdmins);
		        existingGroup.setInstructors(final_groupInstructors);
		        existingGroup.setStudents(final_groupStudents);
		        existingGroup.setAccessLevel(access_level); // Assuming you have this method in your UserGroups class
		        System.out.println("Group updated: " + existingGroup.getGroupName());
		    } else {
		        // Create and add a new group
		        UserGroups groupToAdd = new UserGroups(groupName , final_groupArticles, final_groupAdmins,
		                final_groupInstructors, final_groupStudents, access_level, GroupManager.getSize() + 1);
		        GroupManager.addGroup(groupToAdd);
		        System.out.println("New group added: " + groupName );
		    }
		    GroupInterface groupInterface = new GroupInterface(primaryStage, role);
		});

		// Button that just returns the user to the group settings page.
		returnButton.setOnAction(e -> {
			GroupInterface groupInterface = new GroupInterface(primaryStage, role);
		});

		// HBox holding all buttons
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(returnButton, createGroup);

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Scene signUpScene = new Scene(layout, width, height);
		primaryStage.setScene(signUpScene);
		layout.getChildren().addAll(textPrompts, selectListContainer, selectedListContainer, buttons);
	}


	/***
	 * addUserDialog (Default User): Prompts dialog box when user selects the
	 * permissions they want to allow that user
	 * 
	 * IF SPECIAL: Prompts users to select permissions IF GENERAL: Just add
	 * instructor with admin rights and read rights
	 */
	public boolean addUserDialog(UserClass user, GroupAccess access_level, boolean isEdit) {
	    ArrayList<String> permissions = new ArrayList<>();

	    if (isEdit) {
	        // Preload existing permissions for editing
	        permissions = final_groupInstructors.get(user) != null ? final_groupInstructors.get(user) : new ArrayList<>();
	    }

	    if (access_level == GroupAccess.SPECIAL) {
	        alert.setTitle(isEdit ? "Edit Instructor Permissions" : "Add Instructor to Group");
	        alert.setHeaderText((isEdit ? "Edit" : "Add") + " " + user.getUsername() + " in group?");

	        // Create checkboxes for permissions
	        CheckBox adminPermission = new CheckBox("Allow Admin Rights");
	        adminPermission.setSelected(permissions.contains("admin_rights"));
	        CheckBox readPermission = new CheckBox("Allow this instructor to view decrypted bodies of articles?");
	        readPermission.setSelected(permissions.contains("read_bodies"));

	        // Layout for checkboxes
	        VBox content = new VBox(10);
	        content.getChildren().addAll(adminPermission, readPermission);

	        // Set content on the dialog
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.setContent(content);

	        // Set OK and Cancel buttons
	        ButtonType confirmButton = new ButtonType(isEdit ? "Save Changes" : "Confirm");
	        alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

	        Optional<ButtonType> result = alert.showAndWait();

	        // Check which button was clicked and which checkboxes were selected
	        if (result.isPresent() && result.get() == confirmButton) {
	            System.out.println((isEdit ? "Edited Permissions:" : "Selected Permissions:"));
	            permissions.clear(); // Reset permissions to apply new changes
	            if (adminPermission.isSelected()) {
	                permissions.add("admin_rights");
	                System.out.println(" - Admin");
	            }
	            if (readPermission.isSelected()) {
	                permissions.add("read_bodies");
	                System.out.println(" - allowed to read bodies");
	            }

	            final_groupInstructors.put(user, permissions);
	            return true;
	        }
	        return false;
	    }
	    // General permissions (default)
	    permissions.add("admin_rights");
	    permissions.add("read_bodies");
	    final_groupInstructors.put(user, permissions);
	    return true;
	}

	/***
	 * addUserDialog (Admins): Prompts dialog box when user selects the permissions
	 * they want to allow that user
	 * 
	 * 
	 * IF SPECIAL: Prompts users to select permissions IF GENERAL: Just add admin
	 * with admin rights and read rights
	 */
	public boolean addUserDialog(AdminClass user, GroupAccess access_level, boolean isEdit) {
	    ArrayList<String> permissions = new ArrayList<>();

	    if (isEdit) {
	        // Preload existing permissions for editing
	        permissions = final_groupAdmins.get(user) != null ? final_groupAdmins.get(user) : new ArrayList<>();
	    }

	    if (access_level == GroupAccess.SPECIAL) {
	        alert.setTitle(isEdit ? "Edit Admin Permissions" : "Add Admin to Group");
	        alert.setHeaderText((isEdit ? "Edit" : "Add") + " " + user.getUsername() + " in group?");

	        // Create checkboxes for permissions
	        CheckBox adminPermission = new CheckBox("Allow Admin Rights");
	        adminPermission.setSelected(permissions.contains("admin_rights"));
	        CheckBox readPermission = new CheckBox("Allow this admin to view decrypted bodies of articles?");
	        readPermission.setSelected(permissions.contains("read_bodies"));

	        // Layout for checkboxes
	        VBox content = new VBox(10);
	        content.getChildren().addAll(adminPermission, readPermission);

	        // Set content on the dialog
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.setContent(content);

	        // Set OK and Cancel buttons
	        ButtonType confirmButton = new ButtonType(isEdit ? "Save Changes" : "Confirm");
	        alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

	        Optional<ButtonType> result = alert.showAndWait();

	        // Check which button was clicked and which checkboxes were selected
	        if (result.isPresent() && result.get() == confirmButton) {
	            System.out.println((isEdit ? "Edited Permissions:" : "Selected Permissions:"));
	            permissions.clear(); // Reset permissions to apply new changes
	            if (adminPermission.isSelected()) {
	                permissions.add("admin_rights");
	                System.out.println(" - Admin");
	            }
	            if (readPermission.isSelected()) {
	                permissions.add("read_bodies");
	                System.out.println(" - allowed to read bodies");
	            }

	            final_groupAdmins.put(user, permissions);
	            return true;
	        }
	        return false;
	    }
	    // General permissions (default)
	    permissions.add("admin_rights");
	    permissions.add("read_bodies");
	    final_groupAdmins.put(user, permissions);
	    return true;
	}

	/***
	 * addUserDialog (Students: Prompts dialog box when user selects the permissions
	 * they want to allow that user IF SPECIAL: Prompts users to select permissions
	 * IF GENERAL: Just add instructor with read rights
	 */
	public boolean addUserDialog(StudentClass user, GroupAccess access_level, boolean isEdit) {

		ArrayList<String> permissions = new ArrayList<>();

		if (isEdit) {
			// Preload existing permissions for editing
			permissions = final_groupStudents.get(user) != null ? final_groupStudents.get(user) : new ArrayList<>();
		}

		if (access_level == GroupAccess.SPECIAL) {
			alert.setTitle(isEdit ? "Edit Student Permissions" : "Add Student to Group");
			alert.setHeaderText((isEdit ? "Edit" : "Add") + " " + user.getUsername() + " in group?");

			// Create checkboxes for permissions

			CheckBox readPermission = new CheckBox("Allow this student to view decrypted bodies of articles?");
			readPermission.setSelected(permissions.contains("read_bodies"));

			// Layout for checkboxes
			VBox content = new VBox(10);
			content.getChildren().addAll(readPermission);

			// Set content on the dialog
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.setContent(content);

			// Set OK and Cancel buttons
			ButtonType confirmButton = new ButtonType(isEdit ? "Save Changes" : "Confirm");
			alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

			Optional<ButtonType> result = alert.showAndWait();

			// Check which button was clicked and which checkboxes were selected
			if (result.isPresent() && result.get() == confirmButton) {
				System.out.println((isEdit ? "Edited Permissions:" : "Selected Permissions:"));
				permissions.clear(); // Reset permissions to apply new changes
				if (readPermission.isSelected()) {
					permissions.add("read_bodies");
					System.out.println(" - allowed to read bodies");
				}

				final_groupStudents.put(user, permissions);

				return true;
			}
			return false;

		}
		permissions.add("admin_rights");
		permissions.add("read_bodies");
		final_groupStudents.put(user, permissions);
		return true;

	}




	/***
	 * confirmFirstInstructor: Asks user to confirm if the selected instructor is
	 * allowed to have admin rights.
	 * 
	 * @param user
	 * @return
	 */
	public boolean confirmFirstInstructor(UserClass user) {
		Alert confirm_FirstInstructor = new Alert(Alert.AlertType.CONFIRMATION);
		confirm_FirstInstructor.setHeaderText("Are you sure you want " + user.getUsername()
				+ " to be the first instructor?\nThe first instructor added to a group "
				+ "is given the right to view the bodies of articles in the group and admin rights for this group.");
		confirm_FirstInstructor.setContentText("Click OK to confirm or Cancel to return.");
		ButtonType yes = new ButtonType("yes");
		ButtonType cancel = new ButtonType("cancel");

		Optional<ButtonType> result = confirm_FirstInstructor.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			return true; // User clicked OK
		}
		return false; // User clicked Cancel or closed the dialog

	}

	public <T> boolean removeItem(T item, String itemType) {
		Alert confirm_RemoveItem = new Alert(Alert.AlertType.CONFIRMATION);
		confirm_RemoveItem.setHeaderText("Are you sure you want to remove " 
			    + (itemType.equals("user") ? ((UserClass) item).getUsername() : ((Article) item).getTitle()) 
			    + " from this group?");
		confirm_RemoveItem.setContentText("Click OK to confirm or Cancel to return.");
		ButtonType yes = new ButtonType("yes");
		ButtonType cancel = new ButtonType("cancel");

		Optional<ButtonType> result = confirm_RemoveItem.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			return true; // User clicked OK
		}
		return false; // User clicked Cancel or closed the dialog

	}

}