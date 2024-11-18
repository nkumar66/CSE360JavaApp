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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.CSE360App.AdminClass;
import src.CSE360App.Article;
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
	 *  **IMPORTANT NOTE: WHEN INTEGRATING DATABASE, JUST FILL THESE ARRAY LIST WITH ALL OF DATABASE DATA, 
	 *  THEN WHEN USERS CREATES GROUP, JUST JUST FINAL ARRAY LISTS TO CREATE GROUP OBJECT *** 
	 */
	
	// Temp: Makes fake admins, instructors users and articles for the sake of testing GUI
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

	private ArrayList<StudentClass> final_groupStudents = new ArrayList<>();
	private ArrayList<UserClass> final_groupInstructors = new ArrayList<>();
	private ArrayList<AdminClass> final_groupAdmins = new ArrayList<>();
	
	// Buttons
	private Button returnButton = new Button("Return");
	private Button createGenericGroup = new Button("Create Generic Group");
	private Button createSpecialGroup = new Button("Create Special Group");
	private Button addStudent = new Button("Add Selected Student");
	private Button addInstructor = new Button("Add Selected Instructor");
	private Button addAdmin = new Button("Add Selected Admin");

	private RadioButton studentPermissions = new RadioButton("Student Permissions");
	private RadioButton instructorPermissions = new RadioButton("Instructor Permissions");
	private RadioButton adminPermissions = new RadioButton("Admin Permissions");

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
	 * CreateGroup: Window that allows the custom creation of "Groups" where users
	 * can be given individual permissions, articles can be selected, etc.
	 * 
	 * @param primaryStage
	 * @param role
	 */
	public CreateGroup(Stage primaryStage, String role) {

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
		 *  Below is the collection of clickable fields, where users can add / remove from the group they are creating
		 *  
		 *  ListView objects are the selected lists. All of their attributes are being handled below.
		 */
		ListView<Article> articleView = new ListView<>(articleList);
		ListView<StudentClass> studentView = new ListView<>(studentList);
		setColumnAttributes(studentView);

		ListView<UserClass> instructorView = new ListView<>(instructorList);
		setColumnAttributes(instructorView);

		ListView<AdminClass> adminView = new ListView<>(adminList);
		setColumnAttributes(adminView);

		ListView<Article> selected_articleView = new ListView<>(selected_ArticlesList);

		ListView<StudentClass> selected_userView = new ListView<>(selected_studentList);
		setColumnAttributes(selected_userView);
		ListView<UserClass> selected_InstructorView = new ListView<>(selected_InstructorList);
		setColumnAttributes(selected_InstructorView);

		ListView<AdminClass> selected_AdminView = new ListView<>(selected_AdminsList);
		setColumnAttributes(selected_AdminView);

		// 
		articleView.setPrefWidth(width * .25);
		studentView.setPrefWidth(width * .25);
		instructorView.setPrefWidth(width * .25);
		adminView.setPrefWidth(width * .25);

		selected_articleView.setPrefWidth(width * .25);
		selected_userView.setPrefWidth(width * .25);
		selected_AdminView.setPrefWidth(width * .25);
		selected_InstructorView.setPrefWidth(width * .25);

		/***
		 * The following h/vboxes are used to organize every single selectable list.
		 * The 2 hboxes represent the top and bottom row of lists
		 * The Vboxes represent the containers that hold each list's labels, buttons, etc.
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
		articlesContainer.getChildren().addAll(label_SelectArticles, articleView);
		selectListContainer.getChildren().addAll(usersContainer, instructorsContainer, adminsContainer,
				articlesContainer);

		HBox selectedListContainer = new HBox(10);
		VBox selectedusersContainer = new VBox(10);
		VBox selectedinstructorsContainer = new VBox(10);
		VBox selectedadminsContainer = new VBox(10);
		VBox selectedarticlesContainer = new VBox(10);

		selectedusersContainer.getChildren().addAll(label_selectedUsersList, selected_userView);
		selectedinstructorsContainer.getChildren().addAll(label_selectedInstructorsList, selected_InstructorView);
		selectedadminsContainer.getChildren().addAll(label_selectedAdminsList, selected_AdminView);
		selectedarticlesContainer.getChildren().addAll(label_selectedArticlesList, selected_articleView);
		selectedListContainer.getChildren().addAll(selectedusersContainer, selectedinstructorsContainer,
				selectedadminsContainer, selectedarticlesContainer);

		studentView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && articleView.getSelectionModel().getSelectedItems().size() == 1) {

			}
		});

		// The following button is used to add students to the selected list.
		addStudent.setOnAction(e -> {
			StudentClass userToAdd = studentView.getSelectionModel().getSelectedItem();
			if (addUserDialog(userToAdd)) {

				studentView.getItems().remove(userToAdd);
				selected_studentList.add(userToAdd);
				final_groupStudents.add(userToAdd);
			}
		});

		/***
		 *  The following button is used to add instructors to the selected list.
		 *  It brings up an additonal confirmation dialog, to ensure user is okay with this user getting admin rights.
		 *  
		 */
		addInstructor.setOnAction(e -> {
			UserClass userToAdd = instructorView.getSelectionModel().getSelectedItem();

			if (selected_InstructorList.isEmpty()) {
				if (confirmFirstInstructor(userToAdd)) {
					if (addUserDialog(userToAdd)) {
						instructorView.getItems().remove(userToAdd);
						selected_InstructorList.add(userToAdd);
						final_groupInstructors.add(userToAdd);
					}
				}

			} else {
				if (addUserDialog(userToAdd)) {
					instructorView.getItems().remove(userToAdd);
					selected_InstructorList.add(userToAdd);
					final_groupInstructors.add(userToAdd);
				}
			}

		});

		// The following button is used to add admins to the selected list.
		addAdmin.setOnAction(e -> {
			AdminClass userToAdd = adminView.getSelectionModel().getSelectedItem();
			if (addUserDialog(userToAdd)) {
				adminView.getItems().remove(userToAdd);
				selected_AdminsList.add(userToAdd);
				final_groupAdmins.add(userToAdd);
			}
		});
		
		

		// The following section of code allows the ability to "select" articles by
		// clicking them, which calls display article method
		articleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && articleView.getSelectionModel().getSelectedItems().size() == 1) {

			}
		});

		articleView.setCellFactory(param -> new ListCell<Article>() {

			// Essentially just override what is displayed
			@Override
			protected void updateItem(Article item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTitle()); // Only display the title of the Article
				}
			}
		});

		
		
		// Button that just returns the user to the group settings page.
		returnButton.setOnAction(e -> {
			GroupInterface groupInterface = new GroupInterface(primaryStage, role);
		});

		// HBox holding all buttons
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(returnButton, createGenericGroup, createSpecialGroup);

		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		Scene signUpScene = new Scene(layout, width, height);
		primaryStage.setScene(signUpScene);
		layout.getChildren().addAll(textPrompts, selectListContainer, selectedListContainer, buttons);
	}

	/***
	 * addUserDialog (Default User): Prompts dialog box when user selects the permissions they want
	 * to allow that user
	 */
	public boolean addUserDialog(UserClass user) {
		alert.setTitle("Add User to Group");
		alert.setHeaderText("Add " + user.getUsername() + " to group?");

		// Create checkboxes for permissions
		CheckBox studentPermission = new CheckBox("Allow Student Rights");
		CheckBox instructorPermission = new CheckBox("Allow Instructor Rights");
		CheckBox adminPermission = new CheckBox("Allow Admin Rights");

		// Layout for checkboxes
		VBox content = new VBox(10);
		content.getChildren().addAll(studentPermission, instructorPermission, adminPermission);

		// Set content on the dialog
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setContent(content);

		// Set OK and Cancel buttons
		ButtonType confirmButton = new ButtonType("Confirm");
		alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

		Optional<ButtonType> result = alert.showAndWait();

		// Check which button was clicked and which checkboxes were selected
		if (result.isPresent() && result.get() == confirmButton) {
			System.out.println("Selected Permissions:");
			if (studentPermission.isSelected()) {
				System.out.println(" - Student");
			}
			if (instructorPermission.isSelected()) {
				System.out.println(" - Instructor");
			}
			if (adminPermission.isSelected()) {
				System.out.println(" - Admin");
			}
			return true;
		}
		return false;

	}

	/***
	 * addUserDialog (Admins): Prompts dialog box when user selects the permissions they want
	 * to allow that user
	 */
	public boolean addUserDialog(AdminClass user) {
		alert.setTitle("Add Instructor to Group");
		alert.setHeaderText("Add " + user.getUsername() + " to group?");

		// Create checkboxes for permissions
		CheckBox studentPermission = new CheckBox("Allow Student Rights");
		CheckBox instructorPermission = new CheckBox("Allow Instructor Rights");
		CheckBox adminPermission = new CheckBox("Allow Admin Rights");

		// Layout for checkboxes
		VBox content = new VBox(10);
		content.getChildren().addAll(studentPermission, instructorPermission, adminPermission);

		// Set content on the dialog
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setContent(content);

		// Set OK and Cancel buttons
		ButtonType confirmButton = new ButtonType("Confirm");
		alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

		Optional<ButtonType> result = alert.showAndWait();

		// Check which button was clicked and which checkboxes were selected
		if (result.isPresent() && result.get() == confirmButton) {
			System.out.println("Selected Permissions:");
			if (studentPermission.isSelected()) {
				System.out.println(" - Student");
			}
			if (instructorPermission.isSelected()) {
				System.out.println(" - Instructor");
			}
			if (adminPermission.isSelected()) {
				System.out.println(" - Admin");
			}
			return true;
		}
		return false;

	}

	/***
	 * addUserDialog (Students: Prompts dialog box when user selects the permissions they want
	 * to allow that user
	 */
	public boolean addUserDialog(StudentClass user) {
		alert.setTitle("Add Student to Group");
		alert.setHeaderText("Add " + user.getUsername() + " to group?");

		// Create checkboxes for permissions
		CheckBox studentPermission = new CheckBox("Allow Student Rights");
		CheckBox instructorPermission = new CheckBox("Allow Instructor Rights");
		CheckBox adminPermission = new CheckBox("Allow Admin Rights");

		// Layout for checkboxes
		VBox content = new VBox(10);
		content.getChildren().addAll(studentPermission, instructorPermission, adminPermission);

		// Set content on the dialog
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setContent(content);

		// Set OK and Cancel buttons
		ButtonType confirmButton = new ButtonType("Confirm");
		alert.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

		Optional<ButtonType> result = alert.showAndWait();

		// Check which button was clicked and which checkboxes were selected
		if (result.isPresent() && result.get() == confirmButton) {
			System.out.println("Selected Permissions:");
			if (studentPermission.isSelected()) {
				System.out.println(" - Student");
			}
			if (instructorPermission.isSelected()) {
				System.out.println(" - Instructor");
			}
			if (adminPermission.isSelected()) {
				System.out.println(" - Admin");
			}

			return true;
		}
		return false;

	}

	/***
	 * setColumnAttributes: Helper method that defaults all user-based selectable lists to display by username and role
	 * 
	 * @param <T>
	 * @param listView
	 */
	public <T extends UserClass> void setColumnAttributes(ListView<T> listView) {
		listView.setCellFactory(param -> new ListCell<T>() {
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText("Username: " + item.getUsername() + " Role: " + item.getRoles().get(0));
				}
			}
		});
	}

	/***
	 * confirmFirstInstructor: Asks user to confirm if the selected instructor is allowed to have admin rights.
	 * 
	 * @param user
	 * @return
	 */
	public boolean confirmFirstInstructor(UserClass user) {
		Alert confirm_FirstInstructor = new Alert(Alert.AlertType.CONFIRMATION);
		confirm_FirstInstructor
				.setHeaderText("Are you sure you want " + user.getUsername() + " to be the first instructor?\nThe first instructor added to a group "
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



}