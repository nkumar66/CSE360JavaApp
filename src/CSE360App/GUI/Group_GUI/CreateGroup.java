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
import src.CSE360App.Article;
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
	
	
	// Temp: Makes fake users and articles for the sake of testing GUI 
	private ArrayList<Article> articles = new ArrayList<>();
	private ObservableList<Article> articleList = FXCollections.observableArrayList();
	private ArrayList<UserClass> users = new ArrayList<>();
	private ObservableList<UserClass> userList = FXCollections.observableArrayList();
	
	private ArrayList<Article> selected_Articles = new ArrayList<>();
	private ObservableList<Article> selected_ArticlesList= FXCollections.observableArrayList();
	private ArrayList<UserClass> selected_Users = new ArrayList<>();
	private ObservableList<UserClass> selected_userList = FXCollections.observableArrayList();
	
	
	// Buttons
	private Button returnButton = new Button("Return");
	private Button createGenericGroup = new Button("Create Generic Group");
	private Button createSpecialGroup = new Button("Create Special Group");
	private Button addUser = new Button ("Add Selected User");
	
	private RadioButton studentPermissions = new RadioButton("Student Permissions");
	private RadioButton instructorPermissions = new RadioButton("Instructor Permissions");
	private RadioButton adminPermissions = new RadioButton("Admin Permissions");

	// Labels / Textfields
	private Label label_SelectUser = new Label("Select Users");
	private Label label_SelectArticles = new Label("Select Articles");
	private Label label_GroupTitle = new Label("Group Title: ");
	private Label label_selectedUsersList = new Label("Selected Users");
	private TextField text_GroupTitle = new TextField();
	
	
	// Alerts
	Alert alert = new Alert(AlertType.CONFIRMATION);
	
	
	// Customizable window size for Article reader.
	private int width = 1000;
	private int height = 800;
	
	
	/*** 
	 * CreateGroup: Window that allows the custom creation of "Groups" where users can be given individual permissions, 
	 * 				articles can be selected, etc.
	 * @param primaryStage
	 * @param role
	 */
	public CreateGroup(Stage primaryStage, String role) {
		
	// Process that creates fake articles, and fills clickable list of them.
	Article.populateArticles(articles);
	articleList.addAll(articles);
	
	// Process that creates fake users, and fills clickable list of them.
	UserClass.populateUsers(users);
	userList.addAll(users);
	
	// HBox holding all text prompts for group creation
	HBox textPrompts = new HBox(10);
	text_GroupTitle.setPromptText("Enter Group Title Here");
	textPrompts.getChildren().addAll(label_GroupTitle, text_GroupTitle);
	
	// Clickable lists: The HBox that holds the two individual lsits
	ListView<Article> articleView = new ListView<>(articleList);
	ListView<UserClass> userView = new ListView<>(userList);
	
	ListView<Article> selected_articleView = new ListView<>(selected_ArticlesList);
	ListView<UserClass> selected_userView = new ListView<>(selected_userList);
	
	articleView.setPrefWidth(width * .3);
	userView.setPrefWidth(width * .3);
	
	HBox selectListContainer = new HBox(10);
	VBox leftList = new VBox(10);
	VBox rightList = new VBox(10);
	VBox selected_Users = new VBox(10);
	
	leftList.getChildren().addAll(label_SelectUser, userView, addUser);
	rightList.getChildren().addAll(label_SelectArticles, articleView);
	
	selected_Users.getChildren().addAll(label_selectedUsersList, selected_userView);
	selectListContainer.getChildren().addAll(leftList, rightList, selected_Users);
	
	// The following section of code allows the ability to "select" articles by
	// clicking them, which calls display article method
	articleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		if (newValue != null && articleView.getSelectionModel().getSelectedItems().size() == 1) {
			
		}
	});
	
	addUser.setOnAction(e -> {
		UserClass userToAdd = userView.getSelectionModel().getSelectedItem();
		addUserDialog(userToAdd);
		selected_userList.add(userToAdd);
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
	
	userView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		if (newValue != null && articleView.getSelectionModel().getSelectedItems().size() == 1) {
			
		}
	});
	
	userView.setCellFactory(param -> new ListCell<UserClass>() {

		// Essentially just override what is displayed
		@Override
		protected void updateItem(UserClass item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null) {
				setText(null);
			} else {
				setText("Username: " + item.getUsername() + " Role: " + item.getRoles().get(0)); // Display the username and role of the user
			}
		}
	});
	
	// Button that just returns the user to the group settings page.
	returnButton.setOnAction(e -> {
		GroupInterface groupInterface = new GroupInterface(primaryStage, role);
	});
	
	//HBox holding all buttons
	HBox buttons = new HBox(10);
	buttons.getChildren().addAll(returnButton, createGenericGroup, createSpecialGroup);
	
	
	VBox layout = new VBox(10);
	layout.setAlignment(Pos.CENTER);
	Scene signUpScene = new Scene(layout, width, height);
	primaryStage.setScene(signUpScene);
	layout.getChildren().addAll(textPrompts, selectListContainer, buttons);
}

	
	/***
	 * addUserDialog: Prompts dialog box when user selects the permissions they want to allow that user
	 */
	public void addUserDialog(UserClass user) {
		alert.setTitle("Add User to Group");
		alert.setHeaderText("Add " + user.getUsername() + " to group?");

		   // Create checkboxes for permissions
	    CheckBox studentPermission = new CheckBox("Student");
	    CheckBox instructorPermission = new CheckBox("Instructor");
	    CheckBox adminPermission = new CheckBox("Admin");

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
	    }
	}
	
	
	private void updateListView() {
		articleList.setAll(articles);
		userList.setAll(userList);

	}
	
}